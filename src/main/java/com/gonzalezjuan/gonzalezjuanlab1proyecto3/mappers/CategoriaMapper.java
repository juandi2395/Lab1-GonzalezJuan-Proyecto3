package com.gonzalezjuan.gonzalezjuanlab1proyecto3.mappers;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaResponseDTO toDTO(Categoria categoria);

    Categoria toEntity(CategoriaRequestDTO dto);

    void updateEntity(
            CategoriaRequestDTO dto,
            @MappingTarget Categoria categoria
    );
}