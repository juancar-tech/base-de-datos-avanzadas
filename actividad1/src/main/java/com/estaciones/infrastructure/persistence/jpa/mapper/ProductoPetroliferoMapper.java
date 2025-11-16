package com.estaciones.infrastructure.persistence.jpa.mapper;

import com.estaciones.domain.model.ProductoPetrolifero;
import com.estaciones.infrastructure.persistence.jpa.entity.ProductoPetroliferoEntity;
import com.estaciones.infrastructure.persistence.jpa.repository.JpaProductoPetroliferoRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductoPetroliferoMapper {
    
    private final JpaProductoPetroliferoRepository jpaRepository;
    
    public ProductoPetroliferoMapper(JpaProductoPetroliferoRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    public ProductoPetroliferoEntity toEntity(ProductoPetrolifero domain) {
        if (domain == null) {
            return null;
        }
        
        // Si solo tenemos el ID (nombre y abreviatura son null), buscar la entidad existente
        if (domain.idProducto() != null && 
            (domain.nombreProducto() == null || domain.nombreAbreviatura() == null)) {
            return jpaRepository.findById(domain.idProducto())
                .orElseGet(() -> {
                    // Si no existe, crear una nueva entidad (aunque esto no debería pasar)
                    ProductoPetroliferoEntity entity = new ProductoPetroliferoEntity();
                    entity.setIdProducto(domain.idProducto());
                    entity.setNombreProducto(domain.nombreProducto());
                    entity.setNombreAbreviatura(domain.nombreAbreviatura());
                    return entity;
                });
        }
        
        // Si tenemos todos los datos, crear o actualizar la entidad
        ProductoPetroliferoEntity entity = new ProductoPetroliferoEntity();
        entity.setIdProducto(domain.idProducto());
        entity.setNombreProducto(domain.nombreProducto());
        entity.setNombreAbreviatura(domain.nombreAbreviatura());
        return entity;
    }
    
    public ProductoPetrolifero toDomain(ProductoPetroliferoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ProductoPetrolifero(
            entity.getIdProducto(),
            entity.getNombreProducto(),
            entity.getNombreAbreviatura()
        );
    }
}


