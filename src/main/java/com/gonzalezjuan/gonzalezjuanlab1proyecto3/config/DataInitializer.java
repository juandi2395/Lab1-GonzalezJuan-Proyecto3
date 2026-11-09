package com.gonzalezjuan.gonzalezjuanlab1proyecto3.config;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.admin-password}")
    private String adminPassword;

    @Value("${app.seed.user-password}")
    private String userPassword;

    @Override
    public void run(String... args) {

        crearUsuario(
                "admin",
                adminPassword,
                "SUPER-ADMIN-ROLE"
        );

        crearUsuario(
                "usuario",
                userPassword,
                "USER"
        );
    }

    private void crearUsuario(
            String username,
            String password,
            String nombreRol
    ) {

        if (usuarioRepository.findByUsername(username).isPresent()) {
            return;
        }

        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No existe el rol: " + nombreRol
                        )
                );

        Usuario usuario = new Usuario();

        usuario.setUsername(username);
        usuario.setPassword(
                passwordEncoder.encode(password)
        );

        usuario.setEnabled(true);
        usuario.setRoles(Set.of(rol));

        usuarioRepository.save(usuario);
    }
}