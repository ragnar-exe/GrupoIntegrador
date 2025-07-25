package com.integrador.empleau.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "usuario")
@Getter
@Setter

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Transient
    private String confirmarContrasena;

    @Column(name = "activo")
    private boolean activo = true;

    public Usuario() {
    }
}
