package com.integrador.empleau.model;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "alumno")
public class Alumno{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alumno_id")
    private Integer alumnoId;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "apellido", length = 100)
    private String apellido;

    @Column(name = "dni", unique = true, length = 8)
    private String dni;  

    @Column(name = "universidad" , length = 100)
    private String universidad;

    @Column(name = "carrera" , length = 100)
    private String carrera;

    @Column(name = "ciclo" , length = 20)
    private String ciclo;

    @Column(name = "genero", length = 10)
    private String genero;

    @Column(name = "fecha_nacimiento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @Column(name = "telefono" , length = 9)
    private String telefono;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "cv_path") //Esta anotacion es para mapear a la columna, en la base
    private String cvPath;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "alumno")
    private List<Postulacion> postulacion;

    public Alumno() {

    }
}
