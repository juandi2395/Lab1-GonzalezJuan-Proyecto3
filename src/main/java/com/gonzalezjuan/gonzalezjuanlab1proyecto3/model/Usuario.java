package com.gonzalezjuan.gonzalezjuanlab1proyecto3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true,
            length = 100
    )
    private String username;

    @Column(
            nullable = false,
            length = 255
    )
    private String password;

    @Column(nullable = false)
    private Boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",

            joinColumns = @JoinColumn(
                    name = "usuario_id"
            ),

            inverseJoinColumns = @JoinColumn(
                    name = "rol_id"
            )
    )
    private Set<Rol> roles = new HashSet<>();
}