package com.gonzalezjuan.gonzalezjuanlab1proyecto3.repository;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository
        extends JpaRepository<Categoria, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
}