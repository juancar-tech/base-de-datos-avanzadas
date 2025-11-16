package com.estaciones.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "provincia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinciaEntity {
    
    @Id
    @Column(name = "id_provincia", length = 10)
    private String idProvincia;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ccaa", nullable = false)
    private ComunidadAutonomaEntity comunidadAutonoma;
    
    @Column(name = "nombre_provincia", length = 100, nullable = false)
    private String nombreProvincia;
}


