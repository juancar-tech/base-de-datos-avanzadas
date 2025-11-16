package com.estaciones.domain.model;

import java.time.LocalDateTime;

public record Empresa(
    Long idEmpresa,
    String nombreRotulo,
    LocalDateTime createdAt
) {
    public Empresa withId(Long id) {
        return new Empresa(id, nombreRotulo, createdAt);
    }
}


