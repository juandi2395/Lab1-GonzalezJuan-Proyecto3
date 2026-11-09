package com.gonzalezjuan.gonzalezjuanlab1proyecto3.controllers;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.UsuarioResponseDTO;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {

        return ResponseEntity.ok(
                usuarioService.listar()
        );
    }
}
