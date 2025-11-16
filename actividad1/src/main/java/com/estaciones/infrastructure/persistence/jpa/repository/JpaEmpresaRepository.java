package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.infrastructure.persistence.jpa.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaEmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
    Optional<EmpresaEntity> findByNombreRotulo(String nombreRotulo);
    
    @Query("SELECT e FROM EmpresaEntity e WHERE e.idEmpresa = " +
           "(SELECT est.empresa.idEmpresa FROM EstacionServicioTerrestreEntity est " +
           "GROUP BY est.empresa.idEmpresa ORDER BY COUNT(est) DESC LIMIT 1)")
    Optional<EmpresaEntity> findEmpresaConMasEstacionesTerrestres();
    
    @Query("SELECT e FROM EmpresaEntity e WHERE e.idEmpresa = " +
           "(SELECT est.empresa.idEmpresa FROM EstacionServicioMaritimaEntity est " +
           "GROUP BY est.empresa.idEmpresa ORDER BY COUNT(est) DESC LIMIT 1)")
    Optional<EmpresaEntity> findEmpresaConMasEstacionesMaritimas();
}


