package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.domain.model.Municipio;
import com.estaciones.domain.repository.MunicipioRepository;
import com.estaciones.infrastructure.persistence.jpa.entity.MunicipioEntity;
import com.estaciones.infrastructure.persistence.jpa.mapper.MunicipioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MunicipioRepositoryJpaAdapter implements MunicipioRepository {
    
    private final JpaMunicipioRepository jpaRepository;
    private final MunicipioMapper mapper;
    
    public MunicipioRepositoryJpaAdapter(
            JpaMunicipioRepository jpaRepository,
            MunicipioMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Municipio save(Municipio municipio) {
        Optional<MunicipioEntity> existingEntity = jpaRepository.findById(municipio.idMunicipio());
        
        MunicipioEntity entity;
        if (existingEntity.isPresent()) {
            // Actualizar entidad existente
            entity = existingEntity.get();
            entity.setNombreMunicipio(municipio.nombreMunicipio());
            if (municipio.idProvincia() != null) {
                entity.getProvincia().setIdProvincia(municipio.idProvincia());
            }
            if (municipio.idCcaa() != null) {
                entity.getComunidadAutonoma().setIdCcaa(municipio.idCcaa());
            }
        } else {
            // Crear nueva entidad
            entity = mapper.toEntity(municipio);
        }
        
        MunicipioEntity saved = jpaRepository.saveAndFlush(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Municipio> findById(String idMunicipio) {
        return jpaRepository.findById(idMunicipio)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Municipio> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Municipio> findByProvincia(String idProvincia) {
        return jpaRepository.findByProvincia(idProvincia).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}


