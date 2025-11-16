package com.estaciones.domain.repository;

import com.estaciones.domain.model.Provincia;

import java.util.List;
import java.util.Optional;

public interface ProvinciaRepository {
    Provincia save(Provincia provincia);
    Optional<Provincia> findById(String idProvincia);
    List<Provincia> findAll();
    List<Provincia> findByCcaa(String idCcaa);
}


