package com.estaciones.infrastructure.batch.job;

import com.estaciones.domain.model.EstacionServicioTerrestre;
import com.estaciones.domain.repository.*;
import com.estaciones.infrastructure.batch.reader.LazyEstacionesTerrestresReader;
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
public class CargarEstacionesTerrestresJobConfig {
    
    private static final Logger log = LoggerFactory.getLogger(CargarEstacionesTerrestresJobConfig.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    private final EstacionTerrestreRepository estacionRepository;
    private final EmpresaRepository empresaRepository;
    private final MunicipioRepository municipioRepository;
    private final ProvinciaRepository provinciaRepository;
    private final ComunidadAutonomaRepository ccaaRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    
    public CargarEstacionesTerrestresJobConfig(
            EnergiaApiClient apiClient,
            @Qualifier("terrestresValidator") XmlSchemaValidator validator,
            EstacionTerrestreRepository estacionRepository,
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
    
    @Bean(name = "cargarEstacionesTerrestresJob")
    public Job cargarEstacionesTerrestresJobBean() {
        return new JobBuilder("cargarEstacionesTerrestresJob", jobRepository)
            .start(cargarEstacionesTerrestresStep())
            .build();
    }
    
    @Bean
    public Step cargarEstacionesTerrestresStep() {
        return new StepBuilder("cargarEstacionesTerrestresStep", jobRepository)
            .<EstacionServicioTerrestre, EstacionServicioTerrestre>chunk(50, transactionManager)
            .reader(estacionesTerrestresReader())
            .processor(estacionesTerrestresProcessor())
            .writer(estacionesTerrestresWriter())
            .build();
    }
    
    /**
     * Reader lazy que solo carga los datos cuando se ejecuta el job.
     * No se ejecuta al iniciar la aplicación.
     */
    @Bean
    public ItemReader<EstacionServicioTerrestre> estacionesTerrestresReader() {
        return new LazyEstacionesTerrestresReader(
            apiClient,
            validator,
            empresaRepository,
            municipioRepository,
            provinciaRepository,
            ccaaRepository
        );
    }
    
    @Bean
    public ItemProcessor<EstacionServicioTerrestre, EstacionServicioTerrestre> estacionesTerrestresProcessor() {
        return item -> {
            log.debug("Procesando estación terrestre: {}", item.idEess());
            return item;
        };
    }
    
    @Bean
    public ItemWriter<EstacionServicioTerrestre> estacionesTerrestresWriter() {
        return items -> {
            log.info("=== INICIO ESCRITURA ESTACIONES TERRESTRES ===");
            log.info("Total items recibidos: {}", items.size());
            
            if (items.isEmpty()) {
                log.warn("⚠️ No hay items para escribir!");
                return;
            }
            
            for (EstacionServicioTerrestre estacion : items) {
                try {
                    EstacionServicioTerrestre saved = estacionRepository.save(estacion);
                    log.info("✓ Guardado: ID={}", saved.idEess());
                } catch (Exception e) {
                    log.error("✗ ERROR al guardar: ID={}", estacion.idEess(), e);
                    throw new RuntimeException("Error guardando estación terrestre: " + estacion.idEess(), e);
                }
            }
            
            log.info("=== FIN ESCRITURA ESTACIONES TERRESTRES ===");
        };
    }
}
