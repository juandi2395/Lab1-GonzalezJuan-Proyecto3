package com.gonzalezjuan.gonzalezjuanlab1proyecto3.controllers;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}