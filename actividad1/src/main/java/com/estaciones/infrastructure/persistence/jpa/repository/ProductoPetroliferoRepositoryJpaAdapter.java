package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.domain.model.ProductoPetrolifero;
import com.estaciones.domain.repository.ProductoPetroliferoRepository;
import com.estaciones.infrastructure.persistence.jpa.entity.ProductoPetroliferoEntity;
import com.estaciones.infrastructure.persistence.jpa.mapper.ProductoPetroliferoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductoPetroliferoRepositoryJpaAdapter implements ProductoPetroliferoRepository {
    
    private final JpaProductoPetroliferoRepository jpaRepository;
    private final ProductoPetroliferoMapper mapper;
    
    public ProductoPetroliferoRepositoryJpaAdapter(
            JpaProductoPetroliferoRepository jpaRepository,
            ProductoPetroliferoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public ProductoPetrolifero save(ProductoPetrolifero producto) {
        Optional<ProductoPetroliferoEntity> existingEntity = jpaRepository.findById(producto.idProducto());
        
        ProductoPetroliferoEntity entity;
        if (existingEntity.isPresent()) {
            // Actualizar entidad existente
            entity = existingEntity.get();
            entity.setNombreProducto(producto.nombreProducto());
            entity.setNombreAbreviatura(producto.nombreAbreviatura());
        } else {
            // Crear nueva entidad
            entity = mapper.toEntity(producto);
        }
        
        ProductoPetroliferoEntity saved = jpaRepository.saveAndFlush(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<ProductoPetrolifero> findById(String idProducto) {
        return jpaRepository.findById(idProducto)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<ProductoPetrolifero> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}


