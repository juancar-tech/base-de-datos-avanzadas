package com.estaciones.infrastructure.persistence.jpa.mapper;

import com.estaciones.domain.model.Empresa;
import com.estaciones.infrastructure.persistence.jpa.entity.EmpresaEntity;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {
    
    public EmpresaEntity toEntity(Empresa domain) {
        if (domain == null) {
            return null;
        }
        EmpresaEntity entity = new EmpresaEntity();
        entity.setIdEmpresa(domain.idEmpresa());
        entity.setNombreRotulo(domain.nombreRotulo());
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }
    
    public Empresa toDomain(EmpresaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Empresa(
            entity.getIdEmpresa(),
            entity.getNombreRotulo(),
            entity.getCreatedAt()
        );
    }
}


