package com.estaciones.infrastructure.batch.job;

import com.estaciones.domain.model.ProductoPetrolifero;
import com.estaciones.domain.repository.ProductoPetroliferoRepository;
import com.estaciones.infrastructure.batch.reader.LazyProductosPetroliferosReader;
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
public class CargarProductosPetroliferosJobConfig {
    
    private static final Logger log = LoggerFactory.getLogger(CargarProductosPetroliferosJobConfig.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    private final ProductoPetroliferoRepository repository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    
    public CargarProductosPetroliferosJobConfig(
            EnergiaApiClient apiClient,
            @Qualifier("productosPetroliferosValidator") XmlSchemaValidator validator,
            ProductoPetroliferoRepository repository,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        this.apiClient = apiClient;
        this.validator = validator;
        this.repository = repository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }
    
    @Bean(name = "cargarProductosPetroliferosJob")
    public Job cargarProductosPetroliferosJobBean() {
        return new JobBuilder("cargarProductosPetroliferosJob", jobRepository)
            .start(cargarProductosPetroliferosStep())
            .build();
    }
    
    @Bean
    public Step cargarProductosPetroliferosStep() {
        return new StepBuilder("cargarProductosPetroliferosStep", jobRepository)
            .<ProductoPetrolifero, ProductoPetrolifero>chunk(100, transactionManager)
            .reader(productosPetroliferosReader())
            .processor(productosPetroliferosProcessor())
            .writer(productosPetroliferosWriter())
            .build();
    }
    
    /**
     * Reader lazy que solo carga los datos cuando se ejecuta el job.
     * No se ejecuta al iniciar la aplicación.
     */
    @Bean
    public ItemReader<ProductoPetrolifero> productosPetroliferosReader() {
        return new LazyProductosPetroliferosReader(apiClient, validator);
    }
    
    @Bean
    public ItemProcessor<ProductoPetrolifero, ProductoPetrolifero> productosPetroliferosProcessor() {
        return item -> {
            log.debug("Procesando producto petrolífero: {}", item.nombreProducto());
            return item;
        };
    }
    
    @Bean
    public ItemWriter<ProductoPetrolifero> productosPetroliferosWriter() {
        return items -> {
            log.info("=== INICIO ESCRITURA PRODUCTOS PETROLÍFEROS ===");
            log.info("Total items recibidos: {}", items.size());
            
            if (items.isEmpty()) {
                log.warn("⚠️ No hay items para escribir!");
                return;
            }
            
            for (ProductoPetrolifero producto : items) {
                log.info("Procesando: ID={}, Nombre={}", 
                    producto.idProducto(), producto.nombreProducto());
                try {
                    ProductoPetrolifero saved = repository.save(producto);
                    log.info("✓ Guardado: ID={}, Nombre={}", saved.idProducto(), saved.nombreProducto());
                } catch (Exception e) {
                    log.error("✗ ERROR al guardar: ID={}, Nombre={}", 
                        producto.idProducto(), producto.nombreProducto(), e);
                    throw new RuntimeException("Error guardando producto petrolífero: " + producto.nombreProducto(), e);
                }
            }
            
            log.info("=== FIN ESCRITURA PRODUCTOS PETROLÍFEROS ===");
        };
    }
}
