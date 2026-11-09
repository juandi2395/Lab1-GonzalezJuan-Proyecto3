
package com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto;

import java.util.Set;

public record UsuarioResponseDTO(
        Long id,
        String username,
        Boolean enabled,
        Set<String> roles
) {}
