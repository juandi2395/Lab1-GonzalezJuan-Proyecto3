package com.gonzalezjuan.gonzalezjuanlab1proyecto3.repository;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
}
