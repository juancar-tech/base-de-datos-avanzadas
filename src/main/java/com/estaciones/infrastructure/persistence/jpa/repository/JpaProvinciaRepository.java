package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.infrastructure.persistence.jpa.entity.ProvinciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaProvinciaRepository extends JpaRepository<ProvinciaEntity, String> {
    @Query("SELECT p FROM ProvinciaEntity p WHERE p.comunidadAutonoma.idCcaa = :idCcaa")
    List<ProvinciaEntity> findByCcaa(@Param("idCcaa") String idCcaa);
}


