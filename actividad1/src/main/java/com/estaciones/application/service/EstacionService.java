package com.estaciones.application.service;

import com.estaciones.application.dto.EstacionCercanaDTO;
import com.estaciones.domain.model.EstacionServicioTerrestre;
import com.estaciones.domain.repository.EstacionTerrestreRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionService {
    
    private final EstacionTerrestreRepository estacionTerrestreRepository;
    
    public EstacionService(EstacionTerrestreRepository estacionTerrestreRepository) {
        this.estacionTerrestreRepository = estacionTerrestreRepository;
    }
    
    public List<EstacionCercanaDTO> findEstacionesCercanas(
            BigDecimal latitud, BigDecimal longitud, double radioKm, String idProducto) {
        
        List<EstacionServicioTerrestre> estaciones = 
            estacionTerrestreRepository.findEstacionesCercanas(latitud, longitud, radioKm);
        
        return estaciones.stream()
            .filter(e -> e.precios() != null && !e.precios().isEmpty())
            .filter(e -> idProducto == null || e.precios().stream()
                .anyMatch(p -> p.producto().idProducto().equals(idProducto)))
            .map(e -> {
                var precio = e.precios().stream()
                    .filter(p -> idProducto == null || p.producto().idProducto().equals(idProducto))
                    .findFirst()
                    .map(p -> p.precio())
                    .orElse(BigDecimal.ZERO);
                
                // Calcular distancia aproximada (simplificada)
                double distanciaKm = calcularDistancia(
                    latitud.doubleValue(), longitud.doubleValue(),
                    e.latitud().doubleValue(), e.longitud().doubleValue()
                );
                
                return new EstacionCercanaDTO(
                    e.idEess(),
                    e.direccion(),
                    e.empresa().nombreRotulo(),
                    e.margen(),
                    precio,
                    distanciaKm
                );
            })
            .collect(Collectors.toList());
    }
    
    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la Tierra en km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}


