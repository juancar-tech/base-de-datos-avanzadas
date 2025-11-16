package com.estaciones.domain.repository;

import com.estaciones.domain.model.Municipio;

import java.util.List;
import java.util.Optional;

public interface MunicipioRepository {
    Municipio save(Municipio municipio);
    Optional<Municipio> findById(String idMunicipio);
    List<Municipio> findAll();
    List<Municipio> findByProvincia(String idProvincia);
}


