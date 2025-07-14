package com.linktic.ms_productos.application.services;

import java.util.List;
import java.util.Optional;

import com.linktic.ms_productos.domain.model.Producto;

public interface ProductoRepository {

    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar productos por nombre o precio
    // List<Producto> findByNombreContaining(String nombre);
    // List<Producto> findByPrecioBetween(BigDecimal minPrecio, BigDecimal maxPrecio);

    Producto save(Producto producto);
    
    Optional<Producto> findById(Long id);
    
    List<Producto> findAll();
    
    void deleteById(Long id);
    
    List<Producto> findByProductoId(Long productoId);
}
