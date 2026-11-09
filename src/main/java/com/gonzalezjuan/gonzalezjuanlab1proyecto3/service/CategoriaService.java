package com.gonzalezjuan.gonzalezjuanlab1proyecto3.service;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.exceptions.*;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.mappers.CategoriaMapper;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.Categoria;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final CategoriaMapper categoriaMapper;

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listar() {

        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {

        Categoria categoria = obtenerEntidad(id);

        return categoriaMapper.toDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO registrar(
            CategoriaRequestDTO dto
    ) {

        if (categoriaRepository.existsByNombreIgnoreCase(
                dto.nombre())) {

            throw new BusinessException(
                    "Ya existe una categoría con ese nombre"
            );
        }

        Categoria categoria = categoriaMapper.toEntity(dto);

        Categoria guardada = categoriaRepository.save(categoria);

        return categoriaMapper.toDTO(guardada);
    }

    @Transactional
    public CategoriaResponseDTO actualizar(
            Long id,
            CategoriaRequestDTO dto
    ) {

        Categoria categoria = obtenerEntidad(id);

        categoriaMapper.updateEntity(dto, categoria);

        Categoria guardada = categoriaRepository.save(categoria);

        return categoriaMapper.toDTO(guardada);
    }

    @Transactional
    public void eliminar(Long id) {

        Categoria categoria = obtenerEntidad(id);

        if (productoRepository.existsByCategoriaId(id)) {

            throw new BusinessException(
                    "No se puede eliminar una categoría con productos"
            );
        }

        categoriaRepository.delete(categoria);
    }

    private Categoria obtenerEntidad(Long id) {

        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoría no encontrada: " + id
                        )
                );
    }
}