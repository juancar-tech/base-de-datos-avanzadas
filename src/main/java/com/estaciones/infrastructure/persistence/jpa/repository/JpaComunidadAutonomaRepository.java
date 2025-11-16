package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.infrastructure.persistence.jpa.entity.ComunidadAutonomaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaComunidadAutonomaRepository extends JpaRepository<ComunidadAutonomaEntity, String> {
}


