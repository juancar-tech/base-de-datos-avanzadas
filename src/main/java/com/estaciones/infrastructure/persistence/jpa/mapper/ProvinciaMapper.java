package com.estaciones.infrastructure.persistence.jpa.mapper;

import com.estaciones.domain.model.Provincia;
import com.estaciones.infrastructure.persistence.jpa.entity.ComunidadAutonomaEntity;
import com.estaciones.infrastructure.persistence.jpa.entity.ProvinciaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProvinciaMapper {
    
    public ProvinciaEntity toEntity(Provincia domain) {
        if (domain == null) {
            return null;
        }
        ProvinciaEntity entity = new ProvinciaEntity();
        entity.setIdProvincia(domain.idProvincia());
        entity.setNombreProvincia(domain.nombreProvincia());
        if (domain.idCcaa() != null) {
            ComunidadAutonomaEntity ccaa = new ComunidadAutonomaEntity();
            ccaa.setIdCcaa(domain.idCcaa());
            entity.setComunidadAutonoma(ccaa);
        }
        return entity;
    }
    
    public Provincia toDomain(ProvinciaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Provincia(
            entity.getIdProvincia(),
            entity.getComunidadAutonoma() != null ? entity.getComunidadAutonoma().getIdCcaa() : null,
            entity.getNombreProvincia()
        );
    }
}

