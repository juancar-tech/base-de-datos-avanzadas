package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.infrastructure.persistence.jpa.entity.ProductoPetroliferoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductoPetroliferoRepository extends JpaRepository<ProductoPetroliferoEntity, String> {
}


