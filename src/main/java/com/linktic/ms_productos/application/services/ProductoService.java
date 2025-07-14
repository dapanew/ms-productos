package com.linktic.ms_productos.application.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.linktic.ms_productos.application.exception.ResourceNotFoundException;
import com.linktic.ms_productos.domain.model.Producto;


@Service
//@RequiredArgsConstructor
public class ProductoService implements ProductoUseCase {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }


    @Override
    //crea una logica de negocio para crear un producto
    //verifica si el producto ya existe, si no existe lo crea   
    public Producto createProducto(Producto producto) {
        if (producto.getId() != null && productoRepository.findById(producto.getId()).isPresent()) {
            throw new ResourceNotFoundException("el Producto ya existe en la base de datos", "id", producto.getId());
        }
        return productoRepository.save(producto);
    }
    /* 
    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }*/

    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> getAllProducto() {
        return productoRepository.findAll();
    }

    @Override
    public Producto updateProducto(Long id, Producto productoDetails) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isPresent()) {
            Producto existingProducto = optionalProducto.get();
            existingProducto.setNombre(productoDetails.getNombre());
            existingProducto.setPrecio(productoDetails.getPrecio());
            existingProducto.setFechaPoblamiento(productoDetails.getFechaPoblamiento());
            return productoRepository.save(existingProducto);
  } else {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
    }

    @Override
    public void deleteProducto(Long id) {
        if (!productoRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Producto", "id", id);
        }
        productoRepository.deleteById(id);
    }

}
