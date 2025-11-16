package com.estaciones.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "precio_terrestre", indexes = {
    @Index(name = "idx_precio_terrestre_producto", columnList = "id_producto"),
    @Index(name = "idx_precio_terrestre_fecha", columnList = "fecha_registro")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrecioTerrestreEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_precio")
    private Long idPrecio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_eess", nullable = false)
    private EstacionServicioTerrestreEntity estacion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoPetroliferoEntity producto;
    
    @Column(name = "precio", precision = 6, scale = 3, nullable = false)
    private BigDecimal precio;
    
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;
    
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}


