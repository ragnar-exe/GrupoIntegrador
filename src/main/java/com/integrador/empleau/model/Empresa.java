package com.integrador.empleau.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "empresa")
@Getter
@Setter
public class Empresa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empresa_id")
    private Integer empresaId;

    @Column(name = "nombre_emp" , length = 100)
    private String nombreEmp;

    @Column(name = "razon_social", length = 100)
    private String razonSocial;

    @Column(name = "ruc_emp", unique = true, length = 11)
    private String rucEmp;

    @Column(name = "sector", length = 100)
    private String sector;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "descripcion_emp", length = 255)
    private String descripcionEmp;

    @Column(name = "mision", length = 255)
    private String mision;

    @Column(name = "vision", length = 255)
    private String vision;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    private Usuario usuario;

    

    public Empresa() {

    }
  
}


