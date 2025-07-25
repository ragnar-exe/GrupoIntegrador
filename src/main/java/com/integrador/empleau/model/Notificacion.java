package com.integrador.empleau.model;

import java.time.LocalDateTime;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Column;



@Entity
@Data
@Table(name = "notificacion")
public class Notificacion {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacion_id")
    private Integer id;
    
    @ManyToOne
    private Usuario destinatario; // Puede ser alumno o empresa
    
    @Column(name = "mensaje", length = 255)
    private String mensaje;
   
    @Column(name = "fecha")
    private LocalDateTime fecha;
    
    @Column(name = "leida")
    private boolean leida;
    
    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo; // "NUEVA_POSTULACION", "RESPUESTA_POSTULACION"

    @ManyToOne
    @JoinColumn(name = "postulacion_id", nullable = false)
    private Postulacion postulacion; 

    public Notificacion(){

    }
}

