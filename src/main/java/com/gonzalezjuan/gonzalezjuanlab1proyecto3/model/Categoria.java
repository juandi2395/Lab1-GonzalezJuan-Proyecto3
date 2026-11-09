package com.gonzalezjuan.gonzalezjuanlab1proyecto3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "nombre",
            nullable = false,
            unique = true,
            length = 100
    )
    private String nombre;

    @Column(
            name = "descripcion",
            length = 500
    )
    private String descripcion;
}