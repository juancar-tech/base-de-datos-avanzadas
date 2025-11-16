package com.estaciones.adapter.rest;

import com.estaciones.application.dto.ApiResponse;
import com.estaciones.application.dto.EmpresaDTO;
import com.estaciones.application.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "API de gestión de empresas")
public class EmpresaController {
    
    private final EmpresaService empresaService;
    
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }
    
    @GetMapping("/mas-estaciones-terrestres")
    @Operation(summary = "Obtener empresa con más estaciones terrestres")
    public ResponseEntity<ApiResponse<EmpresaDTO>> getEmpresaConMasEstacionesTerrestres() {
        return empresaService.findEmpresaConMasEstacionesTerrestres()
            .map(empresa -> ResponseEntity.ok(ApiResponse.success(empresa)))
            .orElse(ResponseEntity.ok(ApiResponse.error("No se encontró ninguna empresa")));
    }
}


