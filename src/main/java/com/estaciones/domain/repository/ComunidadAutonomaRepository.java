package com.estaciones.domain.repository;

import com.estaciones.domain.model.ComunidadAutonoma;

import java.util.List;
import java.util.Optional;

public interface ComunidadAutonomaRepository {
    ComunidadAutonoma save(ComunidadAutonoma comunidadAutonoma);
    Optional<ComunidadAutonoma> findById(String idCcaa);
    List<ComunidadAutonoma> findAll();
}


