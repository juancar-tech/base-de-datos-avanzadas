package com.estaciones.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estacion_servicio_terrestre", indexes = {
    @Index(name = "idx_estacion_latitud_longitud", columnList = "latitud,longitud"),
    @Index(name = "idx_estacion_provincia", columnList = "id_provincia"),
    @Index(name = "idx_estacion_ccaa", columnList = "id_ccaa")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstacionServicioTerrestreEntity {
    
    @Id
    @Column(name = "id_eess", length = 20)
    private String idEess;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private EmpresaEntity empresa;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_municipio", nullable = false)
    private MunicipioEntity municipio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_provincia", nullable = false)
    private ProvinciaEntity provincia;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ccaa", nullable = false)
    private ComunidadAutonomaEntity comunidadAutonoma;
    
    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;
    
    @Column(name = "direccion", length = 300)
    private String direccion;
    
    @Column(name = "localidad", length = 150)
    private String localidad;
    
    @Column(name = "margen", length = 10)
    private String margen;
    
    @Column(name = "latitud", precision = 10, scale = 8)
    private BigDecimal latitud;
    
    @Column(name = "longitud", precision = 11, scale = 8)
    private BigDecimal longitud;
    
    @Column(name = "horario", columnDefinition = "TEXT")
    private String horario;
    
    @Column(name = "tipo_venta", length = 50)
    private String tipoVenta;
    
    @Column(name = "remision", length = 10)
    private String remision;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "estacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrecioTerrestreEntity> precios = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}


