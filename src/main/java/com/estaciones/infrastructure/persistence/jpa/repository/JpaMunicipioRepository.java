package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.infrastructure.persistence.jpa.entity.MunicipioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMunicipioRepository extends JpaRepository<MunicipioEntity, String> {
    @Query("SELECT m FROM MunicipioEntity m WHERE m.provincia.idProvincia = :idProvincia")
    List<MunicipioEntity> findByProvincia(@Param("idProvincia") String idProvincia);
}


