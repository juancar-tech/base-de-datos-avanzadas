package com.estaciones.infrastructure.persistence.config;

import com.estaciones.domain.repository.*;
import com.estaciones.infrastructure.persistence.jpa.mapper.*;
import com.estaciones.infrastructure.persistence.jpa.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jpa")
public class PersistenceConfig {
    
    @Bean
    public EmpresaRepository empresaRepository(
            JpaEmpresaRepository jpaRepository,
            EmpresaMapper mapper) {
        return new EmpresaRepositoryJpaAdapter(jpaRepository, mapper);
    }
    
    @Bean
    public ComunidadAutonomaRepository comunidadAutonomaRepository(
            JpaComunidadAutonomaRepository jpaRepository,
            ComunidadAutonomaMapper mapper) {
        return new ComunidadAutonomaRepositoryJpaAdapter(jpaRepository, mapper);
    }
    
    @Bean
    public ProvinciaRepository provinciaRepository(
            JpaProvinciaRepository jpaRepository,
            ProvinciaMapper mapper) {
        return new ProvinciaRepositoryJpaAdapter(jpaRepository, mapper);
    }
    
    @Bean
    public MunicipioRepository municipioRepository(
            JpaMunicipioRepository jpaRepository,
            MunicipioMapper mapper) {
        return new MunicipioRepositoryJpaAdapter(jpaRepository, mapper);
    }
    
    @Bean
    public ProductoPetroliferoRepository productoPetroliferoRepository(
            JpaProductoPetroliferoRepository jpaRepository,
            ProductoPetroliferoMapper mapper) {
        return new ProductoPetroliferoRepositoryJpaAdapter(jpaRepository, mapper);
    }
    
    @Bean
    public EstacionTerrestreRepository estacionTerrestreRepository(
            JpaEstacionTerrestreRepository jpaRepository,
            EstacionTerrestreMapper mapper) {
        return new EstacionTerrestreRepositoryJpaAdapter(jpaRepository, mapper);
    }
}


