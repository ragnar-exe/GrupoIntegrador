package com.integrador.empleau.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrador.empleau.model.Alumno;
import com.integrador.empleau.model.OfertaPractica;
import com.integrador.empleau.model.PostEstado;
import com.integrador.empleau.model.Postulacion;
import com.integrador.empleau.repository.AlumnoRepository;
import com.integrador.empleau.repository.OfertaPracticaRepository;
import com.integrador.empleau.repository.PostulacionRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private OfertaPracticaRepository ofertaRepository;

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Override
    public void postularAlumnoAOferta(String emailAlumno, Integer ofertaId) {
        Alumno alumno = alumnoRepository.findByUsuarioEmail(emailAlumno)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        OfertaPractica oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        // Validar si ya se postuló
        if (postulacionRepository.existsByAlumnoAndOferta(alumno, oferta)) {
            throw new RuntimeException("Ya te has postulado a esta oferta");
        }

        Postulacion postulacion = new Postulacion();
        postulacion.setAlumno(alumno);
        postulacion.setOferta(oferta);
        postulacion.setFechaPostulacion(LocalDateTime.now());
        postulacion.setEstado(PostEstado.PENDIENTE);

        postulacionRepository.save(postulacion);
    }

    @Override
    public List<Postulacion> obtenerPostulacionesPorOfertaId(Integer ofertaId) {
        return postulacionRepository.findByOferta_IdOferta(ofertaId);
    }

    @Override
    public void actualizarEstadoPostulacion(Integer postulacionId, PostEstado nuevoEstado) {

        throw new UnsupportedOperationException("Unimplemented method 'actualizarEstadoPostulacion'");
    }

    @Override
    public List<Postulacion> obtenerPorAlumnoId(Integer alumnoId) {
        return postulacionRepository.findByAlumno_AlumnoId(alumnoId);
    }

    @Override
    @Transactional
    public void actualizarEstadoPostulacion(Integer idPostulacion, String nuevoEstado) {
        Postulacion postulacion = postulacionRepository.findById(idPostulacion)
                .orElseThrow(() -> new IllegalArgumentException("Postulación no encontrada"));

        postulacion.setEstado(PostEstado.valueOf(nuevoEstado));
        postulacionRepository.save(postulacion); // aunque JPA suele hacer flush automáticamente
    }

}
