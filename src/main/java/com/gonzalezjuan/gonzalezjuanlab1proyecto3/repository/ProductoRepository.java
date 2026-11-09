package com.gonzalezjuan.gonzalezjuanlab1proyecto3.repository;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Long> {

    boolean existsByCategoriaId(Long categoriaId);
}