package com.estaciones.infrastructure.batch.config;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {
    
    /**
     * Inicializador del esquema de Spring Batch.
     * Crea las tablas batch_* automáticamente al iniciar la aplicación.
     */
    @Bean
    public DataSourceInitializer batchDataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-postgresql.sql"));
        populator.setContinueOnError(true);
        populator.setSeparator(";");
        
        initializer.setDatabasePopulator(populator);
        initializer.setEnabled(true); // Habilitado para crear las tablas automáticamente
        
        return initializer;
    }
    
    @Bean
    @org.springframework.context.annotation.DependsOn("batchDataSourceInitializer")
    public JobRepository jobRepository(
            DataSource dataSource,
            PlatformTransactionManager transactionManager,
            @Value("${spring.batch.table-prefix:batch_}") String tablePrefix) throws Exception {
        // Asegurar que las tablas de Spring Batch se hayan creado antes de crear el JobRepository
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setTablePrefix(tablePrefix);
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        factory.afterPropertiesSet();
        return factory.getObject();
    }
    
    @Bean
    public JobLauncher asyncJobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
}


