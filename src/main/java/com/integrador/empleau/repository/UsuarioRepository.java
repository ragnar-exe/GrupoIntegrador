package com.integrador.empleau.repository;

import com.integrador.empleau.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

  // Métodos personalizados para buscar usuarios por email
  
  boolean existsByEmail(String email);
  Optional<Usuario> findByEmail(String email);
}
