package com.estaciones.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record EstacionServicioMaritima(
    String idPosteMaritimo,
    Empresa empresa,
    Municipio municipio,
    Provincia provincia,
    ComunidadAutonoma comunidadAutonoma,
    String codigoPostal,
    String direccion,
    String localidad,
    String puerto,
    BigDecimal latitud,
    BigDecimal longitud,
    String horario,
    String tipoVenta,
    String remision,
    LocalDateTime fechaActualizacion,
    List<PrecioMaritimo> precios
) {
    public EstacionServicioMaritima {
        if (precios == null) {
            precios = List.of();
        }
    }
}


