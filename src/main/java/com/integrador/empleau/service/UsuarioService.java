package com.integrador.empleau.service;
import com.integrador.empleau.model.Usuario;

public interface UsuarioService {
    
    Usuario obtenerPorEmail(String email);
    
}
