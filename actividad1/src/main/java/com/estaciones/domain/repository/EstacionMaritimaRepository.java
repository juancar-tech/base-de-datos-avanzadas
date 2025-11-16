package com.estaciones.domain.repository;

import com.estaciones.domain.model.EstacionServicioMaritima;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EstacionMaritimaRepository {
    EstacionServicioMaritima save(EstacionServicioMaritima estacion);
    Optional<EstacionServicioMaritima> findById(String idPosteMaritimo);
    List<EstacionServicioMaritima> findAll();
    List<EstacionServicioMaritima> findEstacionesCercanas(
        BigDecimal latitud, 
        BigDecimal longitud, 
        double radioKm
    );
}



