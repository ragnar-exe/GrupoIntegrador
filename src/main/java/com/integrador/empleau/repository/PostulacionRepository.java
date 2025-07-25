package com.integrador.empleau.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.integrador.empleau.model.Alumno;
import com.integrador.empleau.model.Postulacion;
import com.integrador.empleau.model.OfertaPractica;
import java.util.List;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Integer> {

    // Método para verificar si un alumno ya se postuló a una oferta
    boolean existsByAlumnoAndOferta(Alumno alumno, OfertaPractica oferta);
     List<Postulacion> findByOferta_IdOferta(Integer idOferta);
    List<Postulacion> findByAlumno_AlumnoId(Integer alumnoId);

}
