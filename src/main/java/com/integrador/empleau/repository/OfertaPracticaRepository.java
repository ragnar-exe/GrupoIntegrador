package com.integrador.empleau.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.integrador.empleau.model.OfertaPractica;
import java.util.List;

import java.util.Optional;

public interface OfertaPracticaRepository  extends JpaRepository<OfertaPractica, Integer> {

    // Define methods for managing internship offers
    Optional<OfertaPractica> findById(Integer idOferta);
    List<OfertaPractica> findAll();
    List<OfertaPractica> findByEmpresa_EmpresaId(Integer empresaId);


}
