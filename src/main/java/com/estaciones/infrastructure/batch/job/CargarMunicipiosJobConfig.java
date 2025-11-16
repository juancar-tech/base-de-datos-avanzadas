package com.estaciones.infrastructure.batch.job;

import com.estaciones.domain.model.Municipio;
import com.estaciones.domain.repository.MunicipioRepository;
import com.estaciones.infrastructure.batch.reader.LazyMunicipiosReader;
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
public class CargarMunicipiosJobConfig {
    
    private static final Logger log = LoggerFactory.getLogger(CargarMunicipiosJobConfig.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    private final MunicipioRepository repository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    
    public CargarMunicipiosJobConfig(
            EnergiaApiClient apiClient,
            @Qualifier("municipiosValidator") XmlSchemaValidator validator,
            MunicipioRepository repository,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        this.apiClient = apiClient;
        this.validator = validator;
        this.repository = repository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }
    
    @Bean(name = "cargarMunicipiosJob")
    public Job cargarMunicipiosJobBean() {
        return new JobBuilder("cargarMunicipiosJob", jobRepository)
            .start(cargarMunicipiosStep())
            .build();
    }
    
    @Bean
    public Step cargarMunicipiosStep() {
        return new StepBuilder("cargarMunicipiosStep", jobRepository)
            .<Municipio, Municipio>chunk(100, transactionManager)
            .reader(municipiosReader())
            .processor(municipiosProcessor())
            .writer(municipiosWriter())
            .build();
    }
    
    /**
     * Reader lazy que solo carga los datos cuando se ejecuta el job.
     * No se ejecuta al iniciar la aplicación.
     */
    @Bean
    public ItemReader<Municipio> municipiosReader() {
        return new LazyMunicipiosReader(apiClient, validator);
    }
    
    @Bean
    public ItemProcessor<Municipio, Municipio> municipiosProcessor() {
        return item -> {
            log.debug("Procesando municipio: {}", item.nombreMunicipio());
            return item;
        };
    }
    
    @Bean
    public ItemWriter<Municipio> municipiosWriter() {
        return items -> {
            log.info("=== INICIO ESCRITURA MUNICIPIOS ===");
            log.info("Total items recibidos: {}", items.size());
            
            if (items.isEmpty()) {
                log.warn("⚠️ No hay items para escribir!");
                return;
            }
            
            for (Municipio municipio : items) {
                log.info("Procesando: ID={}, Nombre={}, Provincia={}", 
                    municipio.idMunicipio(), municipio.nombreMunicipio(), municipio.idProvincia());
                try {
                    Municipio saved = repository.save(municipio);
                    log.info("✓ Guardado: ID={}, Nombre={}", saved.idMunicipio(), saved.nombreMunicipio());
                } catch (Exception e) {
                    log.error("✗ ERROR al guardar: ID={}, Nombre={}", 
                        municipio.idMunicipio(), municipio.nombreMunicipio(), e);
                    throw new RuntimeException("Error guardando municipio: " + municipio.nombreMunicipio(), e);
                }
            }
            
            log.info("=== FIN ESCRITURA MUNICIPIOS ===");
        };
    }
}
