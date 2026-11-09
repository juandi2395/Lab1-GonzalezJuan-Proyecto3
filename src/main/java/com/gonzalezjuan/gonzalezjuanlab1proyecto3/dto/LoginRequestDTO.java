package com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank
        String username,

        @NotBlank
        String password

) {
}