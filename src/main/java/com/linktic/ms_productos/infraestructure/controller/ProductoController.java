package com.linktic.ms_productos.infraestructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linktic.ms_productos.application.exception.ResourceNotFoundException;
import com.linktic.ms_productos.application.mapper.ProductoMapper;
import com.linktic.ms_productos.application.services.ProductoUseCase;
import com.linktic.ms_productos.domain.dto.ProductoDTO;
import com.linktic.ms_productos.domain.model.Producto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static net.logstash.logback.argument.StructuredArguments.value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
 @RequestMapping("/api/productos")
 @RequiredArgsConstructor
public class ProductoController {
 
private final ProductoUseCase productoUseCase;
    private final ProductoMapper productoMapper;
    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);


    @PostMapping("/crearProducto")
public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
    final String logPrefix = "crearProducto::";
    final String path = "/api/productos/crearProducto";
    
    log.info("{}Inicio - Path: {}, ID producto: {}", logPrefix, path, productoDTO.getId());
   
    try {
        // Verifica si el ID del producto es nulo dejalo crear
    if (productoDTO.getId() == null) {
            log.warn("{}ID del producto es nulo, se asignará uno automáticamente - Path: {}", logPrefix, path);
    } else {    
        // Verifica si el producto ya existe
        if (productoUseCase.getProductoById(productoDTO.getId()).isPresent()) {
            log.warn("{}Producto ya existe - Path: {}, ID: {}", logPrefix, path, productoDTO.getId());
            throw new ResourceNotFoundException("Producto", "id", productoDTO.getId(),
                    new RuntimeException("El producto ya existe en la base de datos"));
        }
        }
         
           
        Producto producto = productoMapper.toModel(productoDTO);
        Producto createdProducto = productoUseCase.createProducto(producto);
        
        log.info("{}Producto creado exitosamente - Path: {}, ID: {}", 
                logPrefix, path, createdProducto.getId());
        
        return new ResponseEntity<>(productoMapper.toDto(createdProducto), HttpStatus.CREATED);
    } catch (Exception e) {
        log.error("{}Error al crear producto - Path: {}, ID: {}, Error: {}", 
                logPrefix, path, productoDTO.getId(), e.getMessage(), e);
        throw e;
    }
}
    @GetMapping("/todos")
    public ResponseEntity <List<ProductoDTO>> getAllFacturas() {
        List<Producto> productos = productoUseCase.getAllProducto();
        //List<ProductoDTO> productoDTOs = productoMapper.toDtoList(productos);
       
        if (productos.isEmpty()) {
            log.warn("No se encontraron productos");
            return ResponseEntity.noContent().build();
        }
         log.info("Se han encontrado {} productos", productos.size());
        return  ResponseEntity.ok(productoMapper.toDtoList(productos));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) { 
        final String logPrefix = "getProductoById::";   
        final String path = "/api/productos/" + id;
        log.info("{}Inicio - Path: {}, ID producto: {}", logPrefix, path, id);
        
     Producto producto = productoUseCase.getProductoById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        log.info("Producto con ID {} encontrado exitosamente", id);
        return ResponseEntity.ok(productoMapper.toDto(producto));
    }
    @PutMapping("/{id}")
    public ResponseEntity <ProductoDTO> updateFactura(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO) {
        final String logPrefix = "updateProducto::";
        final String path = "/api/productos/" + id; 
        log.info("{}Inicio - Path: {}, ID producto: {}", logPrefix, path, id);
          productoUseCase.getProductoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));

        Producto productoDetails = productoMapper.toModel(productoDTO);
        Producto updatedProducto = productoUseCase.updateProducto(id, productoDetails);
        log.info("Producto con ID {} actualizado exitosamente", id);
        return ResponseEntity.ok(productoMapper.toDto(updatedProducto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        if (!productoUseCase.getProductoById(id).isPresent()) {
            throw new ResourceNotFoundException("Producto", "id", id);
        }
         // Verifica si el producto existe antes de eliminarlo
        log.info("Eliminando producto con ID {}", id);
        productoUseCase.deleteProducto(id);
        log.info("Producto con ID {} eliminado exitosamente", id);
        return ResponseEntity.noContent().build();
    }   
    

    

}
