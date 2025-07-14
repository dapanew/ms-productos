package com.linktic.ms_productos.application.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.linktic.ms_productos.domain.dto.ProductoDTO;
import com.linktic.ms_productos.domain.model.Producto;
import com.linktic.ms_productos.infraestructure.entity.ProductoEntity;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    Producto toModel(ProductoDTO dto);

    ProductoDTO toDto(Producto model);

    List<ProductoDTO> toDtoList(List<Producto> modelList);

    Producto toModel(ProductoEntity entity);

    ProductoEntity toEntity(Producto model);

    List<Producto> toModelList(List<ProductoEntity> entityList);

    List<Producto> toEntityList(List<ProductoDTO> productoDTOs);
}
