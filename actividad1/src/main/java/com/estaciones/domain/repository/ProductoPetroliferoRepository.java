package com.estaciones.domain.repository;

import com.estaciones.domain.model.ProductoPetrolifero;

import java.util.List;
import java.util.Optional;

public interface ProductoPetroliferoRepository {
    ProductoPetrolifero save(ProductoPetrolifero producto);
    Optional<ProductoPetrolifero> findById(String idProducto);
    List<ProductoPetrolifero> findAll();
}


