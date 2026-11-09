package com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto;

import java.math.BigDecimal;

public record ProductoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer cantidadStock,
        Long categoriaId,
        String categoriaNombre
) {
}