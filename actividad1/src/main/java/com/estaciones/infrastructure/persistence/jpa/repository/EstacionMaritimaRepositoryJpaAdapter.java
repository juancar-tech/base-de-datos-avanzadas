package com.estaciones.infrastructure.persistence.jpa.repository;

import com.estaciones.domain.model.EstacionServicioMaritima;
import com.estaciones.domain.repository.EstacionMaritimaRepository;
import com.estaciones.infrastructure.persistence.jpa.entity.EstacionServicioMaritimaEntity;
import com.estaciones.infrastructure.persistence.jpa.mapper.EstacionMaritimaMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EstacionMaritimaRepositoryJpaAdapter implements EstacionMaritimaRepository {
    
    private final JpaEstacionMaritimaRepository jpaRepository;
    private final EstacionMaritimaMapper mapper;
    
    public EstacionMaritimaRepositoryJpaAdapter(
            JpaEstacionMaritimaRepository jpaRepository,
            EstacionMaritimaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public EstacionServicioMaritima save(EstacionServicioMaritima estacion) {
        Optional<EstacionServicioMaritimaEntity> existingEntity = 
            jpaRepository.findById(estacion.idPosteMaritimo());
        
        EstacionServicioMaritimaEntity entity;
        if (existingEntity.isPresent()) {
            // Actualizar entidad existente
            entity = existingEntity.get();
            entity.setCodigoPostal(estacion.codigoPostal());
            entity.setDireccion(estacion.direccion());
            entity.setLocalidad(estacion.localidad());
            entity.setPuerto(estacion.puerto());
            entity.setLatitud(estacion.latitud());
            entity.setLongitud(estacion.longitud());
            entity.setHorario(estacion.horario());
            entity.setTipoVenta(estacion.tipoVenta());
            entity.setRemision(estacion.remision());
            entity.setFechaActualizacion(estacion.fechaActualizacion());
            // Actualizar relaciones si es necesario
            if (estacion.empresa() != null && estacion.empresa().idEmpresa() != null) {
                entity.getEmpresa().setIdEmpresa(estacion.empresa().idEmpresa());
            }
        } else {
            // Crear nueva entidad
            entity = mapper.toEntity(estacion);
        }
        
        EstacionServicioMaritimaEntity saved = jpaRepository.saveAndFlush(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<EstacionServicioMaritima> findById(String idPosteMaritimo) {
        return jpaRepository.findById(idPosteMaritimo)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<EstacionServicioMaritima> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<EstacionServicioMaritima> findEstacionesCercanas(
            BigDecimal latitud, BigDecimal longitud, double radioKm) {
        List<EstacionServicioMaritimaEntity> entities = jpaRepository.findEstacionesCercanas(
            latitud, longitud, radioKm);
        return entities.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}



