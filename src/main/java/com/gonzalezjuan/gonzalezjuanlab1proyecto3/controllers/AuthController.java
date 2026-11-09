
package com.gonzalezjuan.gonzalezjuanlab1proyecto3.controllers;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.service.AuthService;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registro(
            @Valid @RequestBody RegistroUsuarioRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.registrar(request));
    }
}
