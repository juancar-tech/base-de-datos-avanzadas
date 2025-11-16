package com.estaciones.infrastructure.persistence.jpa.mapper;

import com.estaciones.domain.model.*;
import com.estaciones.infrastructure.persistence.jpa.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstacionMaritimaMapper {
    
    private final EmpresaMapper empresaMapper;
    private final MunicipioMapper municipioMapper;
    private final ProvinciaMapper provinciaMapper;
    private final ComunidadAutonomaMapper ccaaMapper;
    private final ProductoPetroliferoMapper productoMapper;
    
    public EstacionMaritimaMapper(
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
    
    public EstacionServicioMaritimaEntity toEntity(EstacionServicioMaritima domain) {
        if (domain == null) {
            return null;
        }
        EstacionServicioMaritimaEntity entity = new EstacionServicioMaritimaEntity();
        entity.setIdPosteMaritimo(domain.idPosteMaritimo());
        entity.setEmpresa(empresaMapper.toEntity(domain.empresa()));
        entity.setMunicipio(municipioMapper.toEntity(domain.municipio()));
        entity.setProvincia(provinciaMapper.toEntity(domain.provincia()));
        entity.setComunidadAutonoma(ccaaMapper.toEntity(domain.comunidadAutonoma()));
        entity.setCodigoPostal(domain.codigoPostal());
        entity.setDireccion(domain.direccion());
        entity.setLocalidad(domain.localidad());
        entity.setPuerto(domain.puerto());
        entity.setLatitud(domain.latitud());
        entity.setLongitud(domain.longitud());
        entity.setHorario(domain.horario());
        entity.setTipoVenta(domain.tipoVenta());
        entity.setRemision(domain.remision());
        entity.setFechaActualizacion(domain.fechaActualizacion());
        
        if (domain.precios() != null && !domain.precios().isEmpty()) {
            List<PrecioMaritimoEntity> preciosEntities = domain.precios().stream()
                .map(precio -> toPrecioEntity(precio, entity))
                .collect(Collectors.toList());
            entity.setPrecios(preciosEntities);
        }
        
        return entity;
    }
    
    public EstacionServicioMaritima toDomain(EstacionServicioMaritimaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new EstacionServicioMaritima(
            entity.getIdPosteMaritimo(),
            empresaMapper.toDomain(entity.getEmpresa()),
            municipioMapper.toDomain(entity.getMunicipio()),
            provinciaMapper.toDomain(entity.getProvincia()),
            ccaaMapper.toDomain(entity.getComunidadAutonoma()),
            entity.getCodigoPostal(),
            entity.getDireccion(),
            entity.getLocalidad(),
            entity.getPuerto(),
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
    
    private PrecioMaritimoEntity toPrecioEntity(PrecioMaritimo domain, EstacionServicioMaritimaEntity estacionEntity) {
        PrecioMaritimoEntity entity = new PrecioMaritimoEntity();
        entity.setIdPrecio(domain.idPrecio());
        entity.setEstacion(estacionEntity);
        entity.setProducto(productoMapper.toEntity(domain.producto()));
        entity.setPrecio(domain.precio());
        entity.setFechaRegistro(domain.fechaRegistro());
        return entity;
    }
    
    private PrecioMaritimo toPrecioDomain(PrecioMaritimoEntity entity) {
        return new PrecioMaritimo(
            entity.getIdPrecio(),
            entity.getEstacion() != null ? entity.getEstacion().getIdPosteMaritimo() : null,
            productoMapper.toDomain(entity.getProducto()),
            entity.getPrecio(),
            entity.getFechaRegistro()
        );
    }
}
