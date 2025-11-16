package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.domain.model.ComunidadAutonoma;
import com.estaciones.domain.repository.ComunidadAutonomaRepository;
import com.estaciones.infrastructure.persistence.jpa.entity.ComunidadAutonomaEntity;
import com.estaciones.infrastructure.persistence.jpa.mapper.ComunidadAutonomaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ComunidadAutonomaRepositoryJpaAdapter implements ComunidadAutonomaRepository {
    
    private final JpaComunidadAutonomaRepository jpaRepository;
    private final ComunidadAutonomaMapper mapper;
    
    public ComunidadAutonomaRepositoryJpaAdapter(
            JpaComunidadAutonomaRepository jpaRepository,
            ComunidadAutonomaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public ComunidadAutonoma save(ComunidadAutonoma comunidadAutonoma) {
        ComunidadAutonomaEntity entity = mapper.toEntity(comunidadAutonoma);
        ComunidadAutonomaEntity saved = jpaRepository.save(entity);
        jpaRepository.flush(); // Forzar flush inmediato
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<ComunidadAutonoma> findById(String idCcaa) {
        return jpaRepository.findById(idCcaa)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<ComunidadAutonoma> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}


