package com.estaciones.infrastructure.persistence.jpa.mapper;

import com.estaciones.domain.model.ComunidadAutonoma;
import com.estaciones.infrastructure.persistence.jpa.entity.ComunidadAutonomaEntity;
import org.springframework.stereotype.Component;

@Component
public class ComunidadAutonomaMapper {
    
    public ComunidadAutonomaEntity toEntity(ComunidadAutonoma domain) {
        if (domain == null) {
            return null;
        }
        ComunidadAutonomaEntity entity = new ComunidadAutonomaEntity();
        entity.setIdCcaa(domain.idCcaa());
        entity.setNombreCcaa(domain.nombreCcaa());
        return entity;
    }
    
    public ComunidadAutonoma toDomain(ComunidadAutonomaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ComunidadAutonoma(
            entity.getIdCcaa(),
            entity.getNombreCcaa()
        );
    }
}


