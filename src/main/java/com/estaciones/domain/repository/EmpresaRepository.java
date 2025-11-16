package com.estaciones.domain.repository;

import com.estaciones.domain.model.Empresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository {
    Empresa save(Empresa empresa);
    Optional<Empresa> findById(Long id);
    Optional<Empresa> findByNombreRotulo(String nombreRotulo);
    List<Empresa> findAll();
    void delete(Empresa empresa);
    
    // Métodos de negocio
    Optional<Empresa> findEmpresaConMasEstacionesTerrestres();
    Optional<Empresa> findEmpresaConMasEstacionesMaritimas();
}


