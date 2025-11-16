package com.estaciones.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BatchService {
    
    private static final Logger log = LoggerFactory.getLogger(BatchService.class);
    
    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final Job cargarComunidadesAutonomasJob;
    private final Job cargarProvinciasJob;
    private final Job cargarMunicipiosJob;
    private final Job cargarProductosPetroliferosJob;
    private final Job cargarEstacionesTerrestresJob;
    private final Job cargarEstacionesMaritimasJob;
    
    public BatchService(
            JobLauncher jobLauncher,
            JobRepository jobRepository,
            @Qualifier("cargarComunidadesAutonomasJob") Job cargarComunidadesAutonomasJob,
            @Qualifier("cargarProvinciasJob") Job cargarProvinciasJob,
            @Qualifier("cargarMunicipiosJob") Job cargarMunicipiosJob,
            @Qualifier("cargarProductosPetroliferosJob") Job cargarProductosPetroliferosJob,
            @Qualifier("cargarEstacionesTerrestresJob") Job cargarEstacionesTerrestresJob,
            @Qualifier("cargarEstacionesMaritimasJob") Job cargarEstacionesMaritimasJob) {
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.cargarComunidadesAutonomasJob = cargarComunidadesAutonomasJob;
        this.cargarProvinciasJob = cargarProvinciasJob;
        this.cargarMunicipiosJob = cargarMunicipiosJob;
        this.cargarProductosPetroliferosJob = cargarProductosPetroliferosJob;
        this.cargarEstacionesTerrestresJob = cargarEstacionesTerrestresJob;
        this.cargarEstacionesMaritimasJob = cargarEstacionesMaritimasJob;
    }
    
    public Long ejecutarCargarComunidadesAutonomas() throws Exception {
        return ejecutarJob("cargarComunidadesAutonomasJob");
    }
    
    public Long ejecutarCargarProvincias() throws Exception {
        return ejecutarJob("cargarProvinciasJob");
    }
    
    public Long ejecutarCargarMunicipios() throws Exception {
        return ejecutarJob("cargarMunicipiosJob");
    }
    
    public Long ejecutarCargarProductosPetroliferos() throws Exception {
        return ejecutarJob("cargarProductosPetroliferosJob");
    }
    
    public Long ejecutarCargarEstacionesTerrestres() throws Exception {
        return ejecutarJob("cargarEstacionesTerrestresJob");
    }
    
    public Long ejecutarCargarEstacionesMaritimas() throws Exception {
        return ejecutarJob("cargarEstacionesMaritimasJob");
    }
    
    public Long ejecutarJob(String jobName) throws Exception {
        log.info("Iniciando job: {}", jobName);
        
        Job job = switch (jobName) {
            case "cargarComunidadesAutonomasJob" -> cargarComunidadesAutonomasJob;
            case "cargarProvinciasJob" -> cargarProvinciasJob;
            case "cargarMunicipiosJob" -> cargarMunicipiosJob;
            case "cargarProductosPetroliferosJob" -> cargarProductosPetroliferosJob;
            case "cargarEstacionesTerrestresJob" -> cargarEstacionesTerrestresJob;
            case "cargarEstacionesMaritimasJob" -> cargarEstacionesMaritimasJob;
            default -> throw new IllegalArgumentException("Job desconocido: " + jobName);
        };
        
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("timestamp", System.currentTimeMillis())
            .toJobParameters();
        
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        
        log.info("Job '{}' iniciado con ID de ejecución: {}", jobName, jobExecution.getId());
        return jobExecution.getId();
    }

    public Long ejecutarTodosLosJobs() throws Exception {
        log.info("=== INICIANDO CARGA COMPLETA DE DATOS ===");
        
        try {
            // 1. Comunidades Autónomas (base)
            log.info("1/6 - Ejecutando job: Comunidades Autónomas");
            JobExecution ccaaExecution = ejecutarJobYEsperar("cargarComunidadesAutonomasJob", cargarComunidadesAutonomasJob);
            log.info("✓ Comunidades Autónomas completado. Estado: {}", ccaaExecution.getStatus());
            
            if (ccaaExecution.getStatus() == BatchStatus.COMPLETED) {
                // 2. Provincias (depende de Comunidades Autónomas)
                log.info("2/6 - Ejecutando job: Provincias");
                JobExecution provinciasExecution = ejecutarJobYEsperar("cargarProvinciasJob", cargarProvinciasJob);
                log.info("✓ Provincias completado. Estado: {}", provinciasExecution.getStatus());
                
                if (provinciasExecution.getStatus() == BatchStatus.COMPLETED) {
                    // 3. Municipios (depende de Provincias y Comunidades Autónomas)
                    log.info("3/6 - Ejecutando job: Municipios");
                    JobExecution municipiosExecution = ejecutarJobYEsperar("cargarMunicipiosJob", cargarMunicipiosJob);
                    log.info("✓ Municipios completado. Estado: {}", municipiosExecution.getStatus());
                } else {
                    log.error("✗ Provincias falló, no se ejecutará Municipios");
                }
            } else {
                log.error("✗ Comunidades Autónomas falló, no se ejecutarán los siguientes jobs");
            }
            
            // 4. Productos Petrolíferos (independiente)
            log.info("4/6 - Ejecutando job: Productos Petrolíferos");
            JobExecution productosExecution = ejecutarJobYEsperar("cargarProductosPetroliferosJob", cargarProductosPetroliferosJob);
            log.info("✓ Productos Petrolíferos completado. Estado: {}", productosExecution.getStatus());
            
            // 5. Estaciones Terrestres (depende de Municipios, Provincias, CCAA y Productos)
            log.info("5/6 - Ejecutando job: Estaciones Terrestres");
            JobExecution terrestresExecution = ejecutarJobYEsperar("cargarEstacionesTerrestresJob", cargarEstacionesTerrestresJob);
            log.info("✓ Estaciones Terrestres completado. Estado: {}", terrestresExecution.getStatus());
            
            // 6. Estaciones Marítimas (depende de Municipios, Provincias, CCAA y Productos)
            log.info("6/6 - Ejecutando job: Estaciones Marítimas");
            JobExecution maritimasExecution = ejecutarJobYEsperar("cargarEstacionesMaritimasJob", cargarEstacionesMaritimasJob);
            log.info("✓ Estaciones Marítimas completado. Estado: {}", maritimasExecution.getStatus());
            
            log.info("=== CARGA COMPLETA FINALIZADA ===");
            return ccaaExecution.getId();
        } catch (Exception e) {
            log.error("✗ Error durante la carga completa de datos", e);
            throw new RuntimeException("Error ejecutando todos los jobs: " + e.getMessage(), e);
        }
    }

    private JobExecution ejecutarJobYEsperar(String jobName, Job job) throws Exception {
        log.info("Iniciando job: {}", jobName);
        
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("timestamp", System.currentTimeMillis())
            .toJobParameters();
        
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        Long executionId = jobExecution.getId();
        
        // Esperar a que el job termine consultando el JobRepository
        log.info("Esperando a que el job '{}' termine... (Execution ID: {})", jobName, executionId);
        int maxWaitTime = 300; // 5 minutos máximo
        int waitTime = 0;
        
        while (waitTime < maxWaitTime) {
            // Consultar el estado actualizado desde el JobRepository
            JobExecution currentExecution = jobRepository.getLastJobExecution(job.getName(), jobParameters);
            
            if (currentExecution != null) {
                BatchStatus status = currentExecution.getStatus();
                
                if (status == BatchStatus.COMPLETED || status == BatchStatus.FAILED || 
                    status == BatchStatus.STOPPED || status == BatchStatus.ABANDONED) {
                    log.info("✓ Job '{}' terminado. Estado: {}", jobName, status);
                    
                    if (status == BatchStatus.FAILED) {
                        log.error("✗ Job '{}' falló. Exit Code: {}, Exit Message: {}", 
                            jobName, currentExecution.getExitStatus().getExitCode(), 
                            currentExecution.getExitStatus().getExitDescription());
                    }
                    
                    return currentExecution;
                }
            }
            
            Thread.sleep(1000); // Esperar 1 segundo
            waitTime++;
            
            if (waitTime % 10 == 0) {
                log.info("Job '{}' aún ejecutándose... ({} segundos)", jobName, waitTime);
            }
        }
        
        log.warn("⚠️ Job '{}' aún ejecutándose después de {} segundos", jobName, maxWaitTime);
        return jobExecution;
    }
    
    private boolean estaEjecutandose(BatchStatus status) {
        return status == BatchStatus.STARTING || status == BatchStatus.STARTED;
    }
}

