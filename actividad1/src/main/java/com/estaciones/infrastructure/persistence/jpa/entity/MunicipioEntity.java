package com.estaciones.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "municipio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MunicipioEntity {
    
    @Id
    @Column(name = "id_municipio", length = 10)
    private String idMunicipio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_provincia", nullable = false)
    private ProvinciaEntity provincia;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ccaa", nullable = false)
    private ComunidadAutonomaEntity comunidadAutonoma;
    
    @Column(name = "nombre_municipio", length = 150, nullable = false)
    private String nombreMunicipio;
}


