package com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto;

public record LoginResponseDTO(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}