package com.integrador.empleau.service;

import com.integrador.empleau.model.Alumno;

public interface AlumnoService {
     
    Alumno registrarAlumno(Alumno alumno);
    Alumno obtenerPorEmail(String email);
    Alumno actualizarAlumno(Alumno alumno);
}
