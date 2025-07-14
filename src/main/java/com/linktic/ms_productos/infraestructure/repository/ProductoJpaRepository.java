package com.linktic.ms_productos.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.linktic.ms_productos.infraestructure.entity.ProductoEntity;

@Repository
public interface ProductoJpaRepository extends JpaRepository <ProductoEntity, Long> {
}
