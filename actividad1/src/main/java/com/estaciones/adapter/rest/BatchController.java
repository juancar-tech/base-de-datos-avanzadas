package com.estaciones.adapter.rest;

import com.estaciones.application.dto.ApiResponse;
import com.estaciones.application.service.BatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
@Tag(name = "Batch", description = "API para gestionar jobs de Spring Batch")
public class BatchController {
    
    private final BatchService batchService;
    
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }
    
    @PostMapping("/jobs/cargar-comunidades-autonomas")
    @Operation(summary = "Ejecutar job para cargar comunidades autónomas")
    public ResponseEntity<ApiResponse<String>> ejecutarCargarComunidadesAutonomas() {
        try {
            Long executionId = batchService.ejecutarCargarComunidadesAutonomas();
            return ResponseEntity.ok(ApiResponse.success(
                "Job iniciado con ID de ejecución: " + executionId
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(
                "Error al ejecutar job: " + e.getMessage()
            ));
        }
    }
    
    @PostMapping("/jobs/cargar-provincias")
    @Operation(summary = "Ejecutar job para cargar provincias")
    public ResponseEntity<ApiResponse<String>> ejecutarCargarProvincias() {
        try {
            Long executionId = batchService.ejecutarCargarProvincias();
            return ResponseEntity.ok(ApiResponse.success(
                "Job iniciado con ID de ejecución: " + executionId
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(
                "Error al ejecutar job: " + e.getMessage()
            ));
        }
    }
    
    @PostMapping("/jobs/cargar-municipios")
    @Operation(summary = "Ejecutar job para cargar municipios")
    public ResponseEntity<ApiResponse<String>> ejecutarCargarMunicipios() {
        try {
            Long executionId = batchService.ejecutarCargarMunicipios();
            return ResponseEntity.ok(ApiResponse.success(
                "Job iniciado con ID de ejecución: " + executionId
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(
                "Error al ejecutar job: " + e.getMessage()
            ));
        }
    }
    
    @PostMapping("/jobs/cargar-productos-petroliferos")
    @Operation(summary = "Ejecutar job para cargar productos petrolíferos")
    public ResponseEntity<ApiResponse<String>> ejecutarCargarProductosPetroliferos() {
        try {
            Long executionId = batchService.ejecutarCargarProductosPetroliferos();
            return ResponseEntity.ok(ApiResponse.success(
                "Job iniciado con ID de ejecución: " + executionId
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(
                "Error al ejecutar job: " + e.getMessage()
            ));
        }
    }
    
    @PostMapping("/jobs/cargar-estaciones-terrestres")
    @Operation(summary = "Ejecutar job para cargar estaciones terrestres")
    public ResponseEntity<ApiResponse<String>> ejecutarCargarEstacionesTerrestres() {
        try {
            Long executionId = batchService.ejecutarCargarEstacionesTerrestres();
            return ResponseEntity.ok(ApiResponse.success(
                "Job iniciado con ID de ejecución: " + executionId
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(
                "Error al ejecutar job: " + e.getMessage()
            ));
        }
    }
    
    @PostMapping("/jobs/cargar-estaciones-maritimas")
    @Operation(summary = "Ejecutar job para cargar estaciones marítimas")
    public ResponseEntity<ApiResponse<String>> ejecutarCargarEstacionesMaritimas() {
        try {
            Long executionId = batchService.ejecutarCargarEstacionesMaritimas();
            return ResponseEntity.ok(ApiResponse.success(
                "Job iniciado con ID de ejecución: " + executionId
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(
                "Error al ejecutar job: " + e.getMessage()
            ));
        }
    }
    
    @PostMapping("/jobs/ejecutar")
    @Operation(summary = "Ejecutar un job por nombre")
    public ResponseEntity<ApiResponse<String>> ejecutarJob(
            @RequestParam("jobName") String jobName) {
        try {
            Long executionId = batchService.ejecutarJob(jobName);
            return ResponseEntity.ok(ApiResponse.success(
                "Job '" + jobName + "' iniciado con ID de ejecución: " + executionId
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(
                "Error al ejecutar job '" + jobName + "': " + e.getMessage()
            ));
        }
    }

    @PostMapping("/jobs/cargar-todos")
    @Operation(summary = "Ejecutar todos los jobs de carga de datos en orden secuencial")
    public ResponseEntity<ApiResponse<String>> ejecutarTodosLosJobs() {
        try {
            Long executionId = batchService.ejecutarTodosLosJobs();
            return ResponseEntity.ok(ApiResponse.success(
                "Todos los jobs ejecutados en secuencia. ID de ejecución inicial: " + executionId
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(
                "Error al ejecutar todos los jobs: " + e.getMessage()
            ));
        }
    }
}


