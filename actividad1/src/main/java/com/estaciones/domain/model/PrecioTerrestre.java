package com.estaciones.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PrecioTerrestre(
    Long idPrecio,
    String idEess,
    ProductoPetrolifero producto,
    BigDecimal precio,
    LocalDateTime fechaRegistro
) {}


