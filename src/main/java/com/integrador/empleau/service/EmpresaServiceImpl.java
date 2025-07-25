package com.integrador.empleau.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.integrador.empleau.model.Empresa;
import com.integrador.empleau.model.Usuario;
import com.integrador.empleau.repository.EmpresaRepository;
import com.integrador.empleau.repository.UsuarioRepository;
import org.springframework.stereotype.Service;


@Service
public class EmpresaServiceImpl implements EmpresaService {
      private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
        public Empresa registrarEmpresa(Empresa empresa) {
            Usuario usuario = empresa.getUsuario();
            System.out.println("Registrando empresa con correo: " + usuario.getEmail());

            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                throw new UsuarioServiceImpl.EmailAlreadyExistsException("Correo ya registrado");
            }

            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            usuario.setFechaRegistro(LocalDateTime.now());
            usuario.setRol("EMPRESA");
            usuario.setActivo(true);

            empresa.setUsuario(usuario);
            Empresa guardada = empresaRepository.save(empresa);
            System.out.println("Empresa registrada con ID: " + guardada.getEmpresaId());
            return guardada;
        }

    public Empresa obtenerPorEmail(String email) {
        return empresaRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Empresa no encontrada"));
    }


    @Override
    public Empresa actualizarEmpresa(Empresa empresaActualizada) {
        Empresa empresaExistente = empresaRepository.findById(empresaActualizada.getEmpresaId())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Empresa no encontrada con ID: " + empresaActualizada.getEmpresaId()));

        // 2. Actualiza campos de la Empresa
        empresaExistente.setNombreEmp(empresaActualizada.getNombreEmp());
        empresaExistente.setRazonSocial(empresaActualizada.getRazonSocial());
        empresaExistente.setSector(empresaActualizada.getSector());
        empresaExistente.setUbicacion(empresaActualizada.getUbicacion());
        empresaExistente.setDescripcionEmp(empresaActualizada.getDescripcionEmp());
        empresaExistente.setMision(empresaActualizada.getMision());
        empresaExistente.setVision(empresaActualizada.getVision());

        // 3. Actualiza datos del Usuario asociado si es necesario
        Usuario usuarioExistente = empresaExistente.getUsuario();
        Usuario usuarioActualizado = empresaActualizada.getUsuario();

        if (usuarioActualizado != null) {
            usuarioExistente.setEmail(usuarioActualizado.getEmail());

        }
        // 4. Guarda la empresa actualizada
        return empresaRepository.save(empresaExistente);
    }

    @Override
    public Empresa obtenerEmpresaPorId(Integer idEmpresa) {
        return empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new UsernameNotFoundException("Empresa no encontrada con ID: " + idEmpresa));
    }

    @Override
    public void eliminarEmpresa(Integer idEmpresa) {
        empresaRepository.deleteById(idEmpresa);
    }

    @Override
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }
        
    
}
