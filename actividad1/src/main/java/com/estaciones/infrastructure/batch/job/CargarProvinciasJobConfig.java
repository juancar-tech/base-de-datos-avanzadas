package com.estaciones.infrastructure.batch.job;

import com.estaciones.domain.model.Provincia;
import com.estaciones.domain.repository.ProvinciaRepository;
import com.estaciones.infrastructure.batch.reader.LazyProvinciasReader;
import com.estaciones.infrastructure.xml.client.EnergiaApiClient;
import com.estaciones.infrastructure.xml.validator.XmlSchemaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CargarProvinciasJobConfig {
    
    private static final Logger log = LoggerFactory.getLogger(CargarProvinciasJobConfig.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    private final ProvinciaRepository repository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    
    public CargarProvinciasJobConfig(
            EnergiaApiClient apiClient,
            @Qualifier("provinciasValidator") XmlSchemaValidator validator,
            ProvinciaRepository repository,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        this.apiClient = apiClient;
        this.validator = validator;
        this.repository = repository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }
    
    @Bean(name = "cargarProvinciasJob")
    public Job cargarProvinciasJobBean() {
        return new JobBuilder("cargarProvinciasJob", jobRepository)
            .start(cargarProvinciasStep())
            .build();
    }
    
    @Bean
    public Step cargarProvinciasStep() {
        return new StepBuilder("cargarProvinciasStep", jobRepository)
            .<Provincia, Provincia>chunk(100, transactionManager)
            .reader(provinciasReader())
            .processor(provinciasProcessor())
            .writer(provinciasWriter())
            .build();
    }
    
    /**
     * Reader lazy que solo carga los datos cuando se ejecuta el job.
     * No se ejecuta al iniciar la aplicación.
     */
    @Bean
    public ItemReader<Provincia> provinciasReader() {
        return new LazyProvinciasReader(apiClient, validator);
    }
    
    @Bean
    public ItemProcessor<Provincia, Provincia> provinciasProcessor() {
        return item -> {
            log.debug("Procesando provincia: {}", item.nombreProvincia());
            return item;
        };
    }
    
    @Bean
    public ItemWriter<Provincia> provinciasWriter() {
        return items -> {
            log.info("=== INICIO ESCRITURA PROVINCIAS ===");
            log.info("Total items recibidos: {}", items.size());
            
            if (items.isEmpty()) {
                log.warn("⚠️ No hay items para escribir!");
                return;
            }
            
            for (Provincia provincia : items) {
                log.info("Procesando: ID={}, Nombre={}, CCAA={}", 
                    provincia.idProvincia(), provincia.nombreProvincia(), provincia.idCcaa());
                try {
                    Provincia saved = repository.save(provincia);
                    log.info("✓ Guardado: ID={}, Nombre={}", saved.idProvincia(), saved.nombreProvincia());
                } catch (Exception e) {
                    log.error("✗ ERROR al guardar: ID={}, Nombre={}", 
                        provincia.idProvincia(), provincia.nombreProvincia(), e);
                    throw new RuntimeException("Error guardando provincia: " + provincia.nombreProvincia(), e);
                }
            }
            
            log.info("=== FIN ESCRITURA PROVINCIAS ===");
        };
    }
}
