package com.integrador.empleau.service;


import com.integrador.empleau.model.OfertaPractica;
import java.util.List;

public interface OfertaPracticaService {

    // Define methods for managing internship offers
    void crearOfertaPractica(OfertaPractica oferta);
    OfertaPractica obtenerOfertaPorId(Integer id);
    List<OfertaPractica> listarOfertas();
    List<OfertaPractica> listarOfertasPorEmpresaId(Integer empresaId);
    OfertaPractica actualizarOfertaPractica(OfertaPractica oferta);
    void eliminarOfertaPractica(Integer id);

    void anularOfertaPractica(OfertaPractica oferta);

}
