package com.estaciones.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto_petrolifero")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPetroliferoEntity {
    
    @Id
    @Column(name = "id_producto", length = 10)
    private String idProducto;
    
    @Column(name = "nombre_producto", length = 150, nullable = false)
    private String nombreProducto;
    
    @Column(name = "nombre_abreviatura", length = 50)
    private String nombreAbreviatura;
}


