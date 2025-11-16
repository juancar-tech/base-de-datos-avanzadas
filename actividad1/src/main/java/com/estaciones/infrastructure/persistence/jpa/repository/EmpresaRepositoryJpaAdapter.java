package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.domain.model.Empresa;
import com.estaciones.domain.repository.EmpresaRepository;
import com.estaciones.infrastructure.persistence.jpa.entity.EmpresaEntity;
import com.estaciones.infrastructure.persistence.jpa.mapper.EmpresaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmpresaRepositoryJpaAdapter implements EmpresaRepository {
    
    private final JpaEmpresaRepository jpaRepository;
    private final EmpresaMapper mapper;
    
    public EmpresaRepositoryJpaAdapter(JpaEmpresaRepository jpaRepository, EmpresaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Empresa save(Empresa empresa) {
        EmpresaEntity entity = mapper.toEntity(empresa);
        EmpresaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Empresa> findById(Long id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Empresa> findByNombreRotulo(String nombreRotulo) {
        return jpaRepository.findByNombreRotulo(nombreRotulo)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Empresa> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public void delete(Empresa empresa) {
        EmpresaEntity entity = mapper.toEntity(empresa);
        jpaRepository.delete(entity);
    }
    
    @Override
    public Optional<Empresa> findEmpresaConMasEstacionesTerrestres() {
        return jpaRepository.findEmpresaConMasEstacionesTerrestres()
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Empresa> findEmpresaConMasEstacionesMaritimas() {
        return jpaRepository.findEmpresaConMasEstacionesMaritimas()
            .map(mapper::toDomain);
    }
}


