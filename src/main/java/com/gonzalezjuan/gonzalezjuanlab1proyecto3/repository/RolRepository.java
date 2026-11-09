package com.gonzalezjuan.gonzalezjuanlab1proyecto3.repository;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository
        extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);
}