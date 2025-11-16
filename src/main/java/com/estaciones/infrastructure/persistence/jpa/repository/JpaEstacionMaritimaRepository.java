package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.infrastructure.persistence.jpa.entity.EstacionServicioMaritimaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface JpaEstacionMaritimaRepository extends JpaRepository<EstacionServicioMaritimaEntity, String> {
    
    @Query(value = """
        WITH distancias AS (
            SELECT est.*, 
                   (6371 * acos(
                       cos(radians(:latitud)) * cos(radians(est.latitud)) * 
                       cos(radians(est.longitud) - radians(:longitud)) + 
                       sin(radians(:latitud)) * sin(radians(est.latitud))
                   )) AS distancia_km
            FROM estacion_servicio_maritima est
            WHERE est.latitud IS NOT NULL AND est.longitud IS NOT NULL
        )
        SELECT * FROM distancias
        WHERE distancia_km <= :radioKm
        ORDER BY distancia_km
        LIMIT 50
        """, nativeQuery = true)
    List<EstacionServicioMaritimaEntity> findEstacionesCercanas(
        @Param("latitud") BigDecimal latitud,
        @Param("longitud") BigDecimal longitud,
        @Param("radioKm") Double radioKm
    );
}



