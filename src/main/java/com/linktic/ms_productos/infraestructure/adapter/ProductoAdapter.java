package com.linktic.ms_productos.infraestructure.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.linktic.ms_productos.application.mapper.ProductoMapper;
import com.linktic.ms_productos.application.services.ProductoRepository;
import com.linktic.ms_productos.domain.model.Producto;
import com.linktic.ms_productos.infraestructure.entity.ProductoEntity;
import com.linktic.ms_productos.infraestructure.repository.ProductoJpaRepository;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class ProductoAdapter implements ProductoRepository {

    private final ProductoJpaRepository productoJpaRepository;
    private final ProductoMapper productoMapper;


    @Override
    public Producto save(Producto producto) {
        ProductoEntity productoEntity = productoMapper.toEntity(producto);
        ProductoEntity savedEntity = productoJpaRepository.save(productoEntity);
        return productoMapper.toModel(savedEntity);
    
    }

    @Override
    public Optional<Producto> findById(Long id) {

        return productoJpaRepository.findById(id)
                .map(productoMapper::toModel);
    }

    @Override
    public List<Producto> findAll() {
        return  productoJpaRepository.findAll().stream()
                .map(productoMapper::toModel)
                .collect(Collectors.toList());
        // productoMapper.toModelList(entities);
    }

    @Override
    public void deleteById(Long id) {
        if (productoJpaRepository.existsById(id)) {
            productoJpaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
    }

    @Override
    public List<Producto> findByProductoId(Long productoId) {
        return productoJpaRepository.findAll().stream()
                .filter(entity -> entity.getId().equals(productoId))
                .map(productoMapper::toModel)
                .collect(Collectors.toList());
    }



    // Implement other methods as needed...

}
