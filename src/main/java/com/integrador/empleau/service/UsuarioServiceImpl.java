package com.integrador.empleau.service;
import org.springframework.stereotype.Service;

import com.integrador.empleau.model.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    
    // Custom exception for email already exists
    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }

    @Override
    public Usuario obtenerPorEmail(String email) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
