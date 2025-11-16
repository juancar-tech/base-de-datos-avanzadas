package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.domain.model.EstacionServicioTerrestre;
import com.estaciones.domain.repository.EstacionTerrestreRepository;
import com.estaciones.infrastructure.persistence.jpa.entity.EstacionServicioTerrestreEntity;
import com.estaciones.infrastructure.persistence.jpa.mapper.EstacionTerrestreMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EstacionTerrestreRepositoryJpaAdapter implements EstacionTerrestreRepository {
    
    private final JpaEstacionTerrestreRepository jpaRepository;
    private final EstacionTerrestreMapper mapper;
    
    public EstacionTerrestreRepositoryJpaAdapter(
            JpaEstacionTerrestreRepository jpaRepository,
            EstacionTerrestreMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public EstacionServicioTerrestre save(EstacionServicioTerrestre estacion) {
        EstacionServicioTerrestreEntity entity = mapper.toEntity(estacion);
        EstacionServicioTerrestreEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<EstacionServicioTerrestre> findById(String idEess) {
        return jpaRepository.findById(idEess)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<EstacionServicioTerrestre> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<EstacionServicioTerrestre> findEstacionesCercanas(
            BigDecimal latitud, BigDecimal longitud, double radioKm) {
        List<EstacionServicioTerrestreEntity> entities = jpaRepository.findEstacionesCercanas(
            latitud, longitud, radioKm);
        return entities.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<EstacionServicioTerrestre> findByProducto(String idProducto) {
        List<EstacionServicioTerrestreEntity> entities = jpaRepository.findByProducto(idProducto);
        return entities.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}

