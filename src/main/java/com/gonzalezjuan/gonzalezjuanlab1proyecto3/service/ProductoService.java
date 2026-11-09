package com.gonzalezjuan.gonzalezjuanlab1proyecto3.service;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.exceptions.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.mappers.ProductoMapper;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listar() {

        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO buscarPorId(Long id) {

        Producto producto = obtenerEntidad(id);

        return productoMapper.toDTO(producto);
    }

    @Transactional
    public ProductoResponseDTO registrar(
            ProductoRequestDTO dto
    ) {

        Categoria categoria = buscarCategoria(dto.categoriaId());

        Producto producto = productoMapper.toEntity(dto);

        producto.setCategoria(categoria);

        Producto guardado = productoRepository.save(producto);

        return productoMapper.toDTO(guardado);
    }

    @Transactional
    public ProductoResponseDTO actualizar(
            Long id,
            ProductoRequestDTO dto
    ) {

        Producto producto = obtenerEntidad(id);

        Categoria categoria = buscarCategoria(dto.categoriaId());

        productoMapper.updateEntity(dto, producto);

        producto.setCategoria(categoria);

        Producto guardado = productoRepository.save(producto);

        return productoMapper.toDTO(guardado);
    }

    @Transactional
    public void eliminar(Long id) {

        Producto producto = obtenerEntidad(id);

        productoRepository.delete(producto);
    }

    private Producto obtenerEntidad(Long id) {

        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Producto no encontrado: " + id
                        )
                );
    }

    private Categoria buscarCategoria(Long id) {

        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoría no encontrada: " + id
                        )
                );
    }
}