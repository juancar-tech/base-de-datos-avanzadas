package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.domain.model.Provincia;
import com.estaciones.domain.repository.ProvinciaRepository;
import com.estaciones.infrastructure.persistence.jpa.entity.ProvinciaEntity;
import com.estaciones.infrastructure.persistence.jpa.mapper.ProvinciaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProvinciaRepositoryJpaAdapter implements ProvinciaRepository {
    
    private final JpaProvinciaRepository jpaRepository;
    private final ProvinciaMapper mapper;
    
    public ProvinciaRepositoryJpaAdapter(
            JpaProvinciaRepository jpaRepository,
            ProvinciaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Provincia save(Provincia provincia) {
        Optional<ProvinciaEntity> existingEntity = jpaRepository.findById(provincia.idProvincia());
        
        ProvinciaEntity entity;
        if (existingEntity.isPresent()) {
            // Actualizar entidad existente
            entity = existingEntity.get();
            entity.setNombreProvincia(provincia.nombreProvincia());
            if (provincia.idCcaa() != null) {
                entity.getComunidadAutonoma().setIdCcaa(provincia.idCcaa());
            }
        } else {
            // Crear nueva entidad
            entity = mapper.toEntity(provincia);
        }
        
        ProvinciaEntity saved = jpaRepository.saveAndFlush(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Provincia> findById(String idProvincia) {
        return jpaRepository.findById(idProvincia)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Provincia> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Provincia> findByCcaa(String idCcaa) {
        return jpaRepository.findByCcaa(idCcaa).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}


