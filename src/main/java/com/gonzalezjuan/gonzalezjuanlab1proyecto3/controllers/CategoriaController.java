package com.gonzalezjuan.gonzalezjuanlab1proyecto3.controllers;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.service.CategoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {

        return ResponseEntity.ok(
                categoriaService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                categoriaService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> registrar(
            @Valid @RequestBody CategoriaRequestDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoriaService.registrar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO dto
    ) {

        return ResponseEntity.ok(
                categoriaService.actualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        categoriaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}