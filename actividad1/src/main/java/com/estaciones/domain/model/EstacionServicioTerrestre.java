package com.estaciones.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record EstacionServicioTerrestre(
    String idEess,
    Empresa empresa,
    Municipio municipio,
    Provincia provincia,
    ComunidadAutonoma comunidadAutonoma,
    String codigoPostal,
    String direccion,
    String localidad,
    String margen,
    BigDecimal latitud,
    BigDecimal longitud,
    String horario,
    String tipoVenta,
    String remision,
    LocalDateTime fechaActualizacion,
    List<PrecioTerrestre> precios
) {
    public EstacionServicioTerrestre {
        if (precios == null) {
            precios = List.of();
        }
    }
}


