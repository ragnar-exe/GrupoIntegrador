package com.integrador.empleau.repository;

import com.integrador.empleau.model.Alumno;
import com.integrador.empleau.model.Usuario;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findByUsuarioEmail(String email);
     Alumno findByUsuario(Usuario usuario);
    
}
