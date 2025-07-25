package com.integrador.empleau.repository;
import com.integrador.empleau.model.Empresa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    Optional<Empresa> findByUsuarioEmail(String email);
    
}

