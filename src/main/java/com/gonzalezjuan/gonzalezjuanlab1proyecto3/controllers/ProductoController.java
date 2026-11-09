package com.gonzalezjuan.gonzalezjuanlab1proyecto3.controllers;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {

        return ResponseEntity.ok(
                productoService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                productoService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> registrar(
            @Valid @RequestBody ProductoRequestDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productoService.registrar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO dto
    ) {

        return ResponseEntity.ok(
                productoService.actualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        productoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}