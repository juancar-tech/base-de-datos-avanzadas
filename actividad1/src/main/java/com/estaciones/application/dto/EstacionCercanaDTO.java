package com.estaciones.application.dto;

import java.math.BigDecimal;

public record EstacionCercanaDTO(
    String idEess,
    String direccion,
    String empresa,
    String margen,
    BigDecimal precio,
    Double distanciaKm
) {}


