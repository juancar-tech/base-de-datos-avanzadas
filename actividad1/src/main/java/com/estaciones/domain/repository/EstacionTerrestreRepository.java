package com.estaciones.domain.repository;

import com.estaciones.domain.model.EstacionServicioTerrestre;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EstacionTerrestreRepository {
    EstacionServicioTerrestre save(EstacionServicioTerrestre estacion);
    Optional<EstacionServicioTerrestre> findById(String idEess);
    List<EstacionServicioTerrestre> findAll();
    List<EstacionServicioTerrestre> findEstacionesCercanas(
        BigDecimal latitud, 
        BigDecimal longitud, 
        double radioKm
    );
    List<EstacionServicioTerrestre> findByProducto(String idProducto);
}


