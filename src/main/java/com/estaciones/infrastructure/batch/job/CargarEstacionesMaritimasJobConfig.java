package com.estaciones.infrastructure.batch.job;

import com.estaciones.domain.model.EstacionServicioMaritima;
import com.estaciones.domain.repository.*;
import com.estaciones.infrastructure.batch.reader.LazyEstacionesMaritimasReader;
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
public class CargarEstacionesMaritimasJobConfig {
    
    private static final Logger log = LoggerFactory.getLogger(CargarEstacionesMaritimasJobConfig.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    private final EstacionMaritimaRepository estacionRepository;
    private final EmpresaRepository empresaRepository;
    private final MunicipioRepository municipioRepository;
    private final ProvinciaRepository provinciaRepository;
    private final ComunidadAutonomaRepository ccaaRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    
    public CargarEstacionesMaritimasJobConfig(
            EnergiaApiClient apiClient,
            @Qualifier("maritimosValidator") XmlSchemaValidator validator,
            EstacionMaritimaRepository estacionRepository,
            EmpresaRepository empresaRepository,
            MunicipioRepository municipioRepository,
            ProvinciaRepository provinciaRepository,
            ComunidadAutonomaRepository ccaaRepository,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        this.apiClient = apiClient;
        this.validator = validator;
        this.estacionRepository = estacionRepository;
        this.empresaRepository = empresaRepository;
        this.municipioRepository = municipioRepository;
        this.provinciaRepository = provinciaRepository;
        this.ccaaRepository = ccaaRepository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }
    
    @Bean(name = "cargarEstacionesMaritimasJob")
    public Job cargarEstacionesMaritimasJobBean() {
        return new JobBuilder("cargarEstacionesMaritimasJob", jobRepository)
            .start(cargarEstacionesMaritimasStep())
            .build();
    }
    
    @Bean
    public Step cargarEstacionesMaritimasStep() {
        return new StepBuilder("cargarEstacionesMaritimasStep", jobRepository)
            .<EstacionServicioMaritima, EstacionServicioMaritima>chunk(50, transactionManager)
            .reader(estacionesMaritimasReader())
            .processor(estacionesMaritimasProcessor())
            .writer(estacionesMaritimasWriter())
            .build();
    }
    
    /**
     * Reader lazy que solo carga los datos cuando se ejecuta el job.
     * No se ejecuta al iniciar la aplicación.
     */
    @Bean
    public ItemReader<EstacionServicioMaritima> estacionesMaritimasReader() {
        return new LazyEstacionesMaritimasReader(
            apiClient,
            validator,
            empresaRepository,
            municipioRepository,
            provinciaRepository,
            ccaaRepository
        );
    }
    
    @Bean
    public ItemProcessor<EstacionServicioMaritima, EstacionServicioMaritima> estacionesMaritimasProcessor() {
        return item -> {
            log.debug("Procesando estación marítima: {}", item.idPosteMaritimo());
            return item;
        };
    }
    
    @Bean
    public ItemWriter<EstacionServicioMaritima> estacionesMaritimasWriter() {
        return items -> {
            log.info("=== INICIO ESCRITURA ESTACIONES MARÍTIMAS ===");
            log.info("Total items recibidos: {}", items.size());
            
            if (items.isEmpty()) {
                log.warn("No hay items para escribir!");
                return;
            }
            
            for (EstacionServicioMaritima estacion : items) {
                try {
                    EstacionServicioMaritima saved = estacionRepository.save(estacion);
                    log.info("✓ Guardado: ID={}", saved.idPosteMaritimo());
                } catch (Exception e) {
                    log.error("✗ ERROR al guardar: ID={}", estacion.idPosteMaritimo(), e);
                    throw new RuntimeException("Error guardando estación marítima: " + estacion.idPosteMaritimo(), e);
                }
            }
            
            log.info("=== FIN ESCRITURA ESTACIONES MARÍTIMAS ===");
        };
    }
}
