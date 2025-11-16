package com.estaciones.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PrecioMaritimo(
    Long idPrecio,
    String idPosteMaritimo,
    ProductoPetrolifero producto,
    BigDecimal precio,
    LocalDateTime fechaRegistro
) {}


