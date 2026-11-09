package com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductoRequestDTO(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150)
        String nombre,

        @Size(max = 500)
        String descripcion,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0")
        BigDecimal precio,

        @NotNull(message = "El stock es obligatorio")
        @Min(0)
        Integer cantidadStock,

        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId

) {
}