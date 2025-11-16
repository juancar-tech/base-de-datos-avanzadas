package com.estaciones.infrastructure.persistence.jpa.mapper;

import com.estaciones.domain.model.*;
import com.estaciones.infrastructure.persistence.jpa.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstacionTerrestreMapper {
    
    private final EmpresaMapper empresaMapper;
    private final MunicipioMapper municipioMapper;
    private final ProvinciaMapper provinciaMapper;
    private final ComunidadAutonomaMapper ccaaMapper;
    private final ProductoPetroliferoMapper productoMapper;
    
    public EstacionTerrestreMapper(
            EmpresaMapper empresaMapper,
            MunicipioMapper municipioMapper,
            ProvinciaMapper provinciaMapper,
            ComunidadAutonomaMapper ccaaMapper,
            ProductoPetroliferoMapper productoMapper) {
        this.empresaMapper = empresaMapper;
        this.municipioMapper = municipioMapper;
        this.provinciaMapper = provinciaMapper;
        this.ccaaMapper = ccaaMapper;
        this.productoMapper = productoMapper;
    }
    
    public EstacionServicioTerrestreEntity toEntity(EstacionServicioTerrestre domain) {
        if (domain == null) {
            return null;
        }
        EstacionServicioTerrestreEntity entity = new EstacionServicioTerrestreEntity();
        entity.setIdEess(domain.idEess());
        entity.setEmpresa(empresaMapper.toEntity(domain.empresa()));
        entity.setMunicipio(municipioMapper.toEntity(domain.municipio()));
        entity.setProvincia(provinciaMapper.toEntity(domain.provincia()));
        entity.setComunidadAutonoma(ccaaMapper.toEntity(domain.comunidadAutonoma()));
        entity.setCodigoPostal(domain.codigoPostal());
        entity.setDireccion(domain.direccion());
        entity.setLocalidad(domain.localidad());
        entity.setMargen(domain.margen());
        entity.setLatitud(domain.latitud());
        entity.setLongitud(domain.longitud());
        entity.setHorario(domain.horario());
        entity.setTipoVenta(domain.tipoVenta());
        entity.setRemision(domain.remision());
        entity.setFechaActualizacion(domain.fechaActualizacion());
        
        if (domain.precios() != null && !domain.precios().isEmpty()) {
            List<PrecioTerrestreEntity> preciosEntities = domain.precios().stream()
                .map(precio -> toPrecioEntity(precio, entity))
                .collect(Collectors.toList());
            entity.setPrecios(preciosEntities);
        }
        
        return entity;
    }
    
    public EstacionServicioTerrestre toDomain(EstacionServicioTerrestreEntity entity) {
        if (entity == null) {
            return null;
        }
        return new EstacionServicioTerrestre(
            entity.getIdEess(),
            empresaMapper.toDomain(entity.getEmpresa()),
            municipioMapper.toDomain(entity.getMunicipio()),
            provinciaMapper.toDomain(entity.getProvincia()),
            ccaaMapper.toDomain(entity.getComunidadAutonoma()),
            entity.getCodigoPostal(),
            entity.getDireccion(),
            entity.getLocalidad(),
            entity.getMargen(),
            entity.getLatitud(),
            entity.getLongitud(),
            entity.getHorario(),
            entity.getTipoVenta(),
            entity.getRemision(),
            entity.getFechaActualizacion(),
            entity.getPrecios() != null ? entity.getPrecios().stream()
                .map(this::toPrecioDomain)
                .collect(Collectors.toList()) : List.of()
        );
    }
    
    private PrecioTerrestreEntity toPrecioEntity(PrecioTerrestre domain, EstacionServicioTerrestreEntity estacionEntity) {
        PrecioTerrestreEntity entity = new PrecioTerrestreEntity();
        entity.setIdPrecio(domain.idPrecio());
        entity.setEstacion(estacionEntity);
        entity.setProducto(productoMapper.toEntity(domain.producto()));
        entity.setPrecio(domain.precio());
        entity.setFechaRegistro(domain.fechaRegistro());
        return entity;
    }
    
    private PrecioTerrestre toPrecioDomain(PrecioTerrestreEntity entity) {
        return new PrecioTerrestre(
            entity.getIdPrecio(),
            entity.getEstacion() != null ? entity.getEstacion().getIdEess() : null,
            productoMapper.toDomain(entity.getProducto()),
            entity.getPrecio(),
            entity.getFechaRegistro()
        );
    }
}


