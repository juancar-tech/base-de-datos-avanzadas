package com.estaciones.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comunidad_autonoma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComunidadAutonomaEntity {
    
    @Id
    @Column(name = "id_ccaa", length = 10)
    private String idCcaa;
    
    @Column(name = "nombre_ccaa", length = 100, nullable = false, unique = true)
    private String nombreCcaa;
}


