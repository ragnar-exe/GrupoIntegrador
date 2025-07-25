package com.integrador.empleau.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "oferta_practica")
public class OfertaPractica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta")
    private Integer idOferta;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 2000)
    private String descripcion;

    @Column(length = 500)
    private String requisitos;

   @Enumerated(EnumType.STRING)
    private Estado estado;  // "ABIERTA", "CERRADA", "CANCELADA"

    @Column(nullable = false)
    private int vacantes;

    @Column(name = "fecha_limite", nullable = false)
    private LocalDate fechaLimite;

    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDate fechaPublicacion;

    // Relación con Empresa (muchas ofertas pueden ser de una empresa)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    // Constructor vacío
    public OfertaPractica() {
    }

}

