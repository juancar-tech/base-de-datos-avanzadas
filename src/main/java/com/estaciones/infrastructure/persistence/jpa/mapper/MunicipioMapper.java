package com.estaciones.infrastructure.persistence.jpa.mapper;

import com.estaciones.domain.model.Municipio;
import com.estaciones.infrastructure.persistence.jpa.entity.ComunidadAutonomaEntity;
import com.estaciones.infrastructure.persistence.jpa.entity.MunicipioEntity;
import com.estaciones.infrastructure.persistence.jpa.entity.ProvinciaEntity;
import org.springframework.stereotype.Component;

@Component
public class MunicipioMapper {
    
    public MunicipioEntity toEntity(Municipio domain) {
        if (domain == null) {
            return null;
        }
        MunicipioEntity entity = new MunicipioEntity();
        entity.setIdMunicipio(domain.idMunicipio());
        entity.setNombreMunicipio(domain.nombreMunicipio());
        
        if (domain.idProvincia() != null) {
            ProvinciaEntity provincia = new ProvinciaEntity();
            provincia.setIdProvincia(domain.idProvincia());
            entity.setProvincia(provincia);
        }
        
        if (domain.idCcaa() != null) {
            ComunidadAutonomaEntity ccaa = new ComunidadAutonomaEntity();
            ccaa.setIdCcaa(domain.idCcaa());
            entity.setComunidadAutonoma(ccaa);
        }
        
        return entity;
    }
    
    public Municipio toDomain(MunicipioEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Municipio(
            entity.getIdMunicipio(),
            entity.getProvincia() != null ? entity.getProvincia().getIdProvincia() : null,
            entity.getComunidadAutonoma() != null ? entity.getComunidadAutonoma().getIdCcaa() : null,
            entity.getNombreMunicipio()
        );
    }
}

