package com.estaciones.adapter.rest;

import com.estaciones.application.dto.ApiResponse;
import com.estaciones.application.dto.EstacionCercanaDTO;
import com.estaciones.application.service.EstacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/estaciones")
@Tag(name = "Estaciones", description = "API de gestión de estaciones de servicio")
public class EstacionController {
    
    private final EstacionService estacionService;
    
    public EstacionController(EstacionService estacionService) {
        this.estacionService = estacionService;
    }
    
    @GetMapping("/cercanas")
    @Operation(summary = "Buscar estaciones cercanas a una ubicación")
    public ResponseEntity<ApiResponse<List<EstacionCercanaDTO>>> findEstacionesCercanas(
            @Parameter(description = "Latitud", required = true)
            @RequestParam("lat") BigDecimal latitud,
            @Parameter(description = "Longitud", required = true)
            @RequestParam("lon") BigDecimal longitud,
            @Parameter(description = "Radio de búsqueda en kilómetros", required = true)
            @RequestParam("radio") double radioKm,
            @Parameter(description = "ID del producto petrolífero (opcional)")
            @RequestParam(value = "combustible", required = false) String idProducto) {
        
        List<EstacionCercanaDTO> estaciones = estacionService.findEstacionesCercanas(
            latitud, longitud, radioKm, idProducto);
        
        return ResponseEntity.ok(ApiResponse.success(estaciones));
    }
}


