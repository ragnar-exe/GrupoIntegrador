package com.integrador.empleau.service;

import com.integrador.empleau.model.Alumno;
import com.integrador.empleau.model.Usuario;
import com.integrador.empleau.repository.AlumnoRepository;
import com.integrador.empleau.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AlumnoServiceImpl(AlumnoRepository alumnoRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.alumnoRepository = alumnoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Alumno registrarAlumno(Alumno alumno) {
        Usuario usuario = alumno.getUsuario();

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new UsuarioServiceImpl.EmailAlreadyExistsException("Correo ya registrado");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setRol("ALUMNO");
        usuario.setActivo(true);

        alumno.setUsuario(usuario); // Relación establecida

        return alumnoRepository.save(alumno); // Gracias al CascadeType.ALL, también guarda el usuario
    }

    public Alumno obtenerPorEmail(String email) {
        return alumnoRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Alumno no encontrado"));
    }

    @Override
    public Alumno actualizarAlumno(Alumno alumnoActualizado) {
        Alumno alumnoExistente = alumnoRepository.findById(alumnoActualizado.getAlumnoId().longValue())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Alumno no encontrado con ID: " + alumnoActualizado.getAlumnoId()));

        // 2. Actualiza campos del Alumno
        alumnoExistente.setNombre(alumnoActualizado.getNombre());
        alumnoExistente.setApellido(alumnoActualizado.getApellido());
        alumnoExistente.setTelefono(alumnoActualizado.getTelefono());
        alumnoExistente.setCarrera(alumnoActualizado.getCarrera());
        alumnoExistente.setFechaNacimiento(alumnoActualizado.getFechaNacimiento());
        alumnoExistente.setUbicacion(alumnoActualizado.getUbicacion());
        alumnoExistente.setCiclo(alumnoActualizado.getCiclo());

        // 3. Actualiza datos del Usuario asociado si es necesario
        Usuario usuarioExistente = alumnoExistente.getUsuario();
        Usuario usuarioActualizado = alumnoActualizado.getUsuario();

        if (usuarioActualizado != null) {
            usuarioExistente.setEmail(usuarioActualizado.getEmail());

        }
        // 4. Guarda el alumno actualizado
        return alumnoRepository.save(alumnoExistente);
    }
}
