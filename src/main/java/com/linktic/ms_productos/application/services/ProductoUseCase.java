package com.linktic.ms_productos.application.services;

import java.util.List;
import java.util.Optional;

import com.linktic.ms_productos.domain.model.Producto;

public interface ProductoUseCase {
    Producto createProducto(Producto producto);
    
    Optional<Producto> getProductoById(Long id);
    
    List<Producto> getAllProducto();
    
    Producto updateProducto(Long id, Producto productoDetails);
    
    void deleteProducto(Long id);
}
