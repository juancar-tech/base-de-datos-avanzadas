package com.estaciones.infrastructure.batch.job;

import com.estaciones.domain.model.ComunidadAutonoma;
import com.estaciones.domain.repository.ComunidadAutonomaRepository;
import com.estaciones.infrastructure.batch.reader.LazyComunidadesAutonomasReader;
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
public class CargarComunidadesAutonomasJobConfig {
    
    private static final Logger log = LoggerFactory.getLogger(CargarComunidadesAutonomasJobConfig.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    private final ComunidadAutonomaRepository repository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    
    public CargarComunidadesAutonomasJobConfig(
            EnergiaApiClient apiClient,
            @Qualifier("comunidadesAutonomasValidator") XmlSchemaValidator validator,
            ComunidadAutonomaRepository repository,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        this.apiClient = apiClient;
        this.validator = validator;
        this.repository = repository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }
    
    @Bean(name = "cargarComunidadesAutonomasJob")
    public Job cargarComunidadesAutonomasJobBean() {
        return new JobBuilder("cargarComunidadesAutonomasJob", jobRepository)
            .start(cargarComunidadesAutonomasStep())
            .build();
    }
    
    @Bean
    public Step cargarComunidadesAutonomasStep() {
        return new StepBuilder("cargarComunidadesAutonomasStep", jobRepository)
            .<ComunidadAutonoma, ComunidadAutonoma>chunk(100, transactionManager)
            .reader(comunidadesAutonomasReader())
            .processor(comunidadesAutonomasProcessor())
            .writer(comunidadesAutonomasWriter())
            .build();
    }
    
    /**
     * Reader lazy que solo carga los datos cuando se ejecuta el job.
     * No se ejecuta al iniciar la aplicación.
     */
    @Bean
    public ItemReader<ComunidadAutonoma> comunidadesAutonomasReader() {
        return new LazyComunidadesAutonomasReader(apiClient, validator);
    }
    
    @Bean
    public ItemProcessor<ComunidadAutonoma, ComunidadAutonoma> comunidadesAutonomasProcessor() {
        return item -> {
            log.debug("Procesando comunidad autónoma: {}", item.nombreCcaa());
            return item;
        };
    }
    
    @Bean
    public ItemWriter<ComunidadAutonoma> comunidadesAutonomasWriter() {
        return items -> {
            log.info("=== INICIO ESCRITURA ===");
            log.info("Total items recibidos: {}", items.size());
            
            if (items.isEmpty()) {
                log.warn("⚠️ No hay items para escribir!");
                return;
            }
            
            for (ComunidadAutonoma ccaa : items) {
                log.info("Procesando: ID={}, Nombre={}", ccaa.idCcaa(), ccaa.nombreCcaa());
                try {
                    ComunidadAutonoma saved = repository.save(ccaa);
                    log.info("✓ Guardado: ID={}, Nombre={}", saved.idCcaa(), saved.nombreCcaa());
                } catch (Exception e) {
                    log.error("✗ ERROR al guardar: ID={}, Nombre={}", ccaa.idCcaa(), ccaa.nombreCcaa(), e);
                    // Re-lanzar la excepción para que Spring Batch la maneje
                    throw new RuntimeException("Error guardando comunidad autónoma: " + ccaa.nombreCcaa(), e);
                }
            }
            
            log.info("=== FIN ESCRITURA ===");
        };
    }
}

