package com.gonzalezjuan.gonzalezjuanlab1proyecto3.mappers;


import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.Producto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(
            target = "categoriaId",
            source = "categoria.id"
    )
    @Mapping(
            target = "categoriaNombre",
            source = "categoria.nombre"
    )
    ProductoResponseDTO toDTO(Producto producto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Producto toEntity(ProductoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateEntity(
            ProductoRequestDTO dto,
            @MappingTarget Producto producto
    );
}