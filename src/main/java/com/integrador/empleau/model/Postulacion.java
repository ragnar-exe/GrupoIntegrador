package com.integrador.empleau.model;

import java.time.LocalDateTime;
import lombok.Data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;

@Data
@Entity
@Table(name = "postulacion")
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postulacion")
    private Integer idPostulacion;

    @ManyToOne
    @JoinColumn(name = "id_oferta")
    private OfertaPractica oferta;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @Column(name = "fecha_postulacion")
    private LocalDateTime fechaPostulacion;

   @Enumerated(EnumType.STRING)
    private PostEstado estado; // "PENDIENTE", "ACEPTADA", "RECHAZADA"

    public Postulacion() {
        // Constructor vacío
    }

}
