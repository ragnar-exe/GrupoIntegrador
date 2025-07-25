package com.integrador.empleau.service;

import com.integrador.empleau.model.PostEstado;
import com.integrador.empleau.model.Postulacion;
import java.util.List;

public interface PostulacionService {

    void postularAlumnoAOferta(String emailAlumno, Integer ofertaId);
    List<Postulacion> obtenerPostulacionesPorOfertaId(Integer ofertaId);
    void actualizarEstadoPostulacion(Integer postulacionId, PostEstado nuevoEstado);  
    List<Postulacion> obtenerPorAlumnoId(Integer alumnoId);
    void actualizarEstadoPostulacion(Integer idPostulacion, String nuevoEstado);
    
}
