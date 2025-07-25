package com.integrador.empleau.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.integrador.empleau.model.Empresa;
import com.integrador.empleau.model.Usuario;
import com.integrador.empleau.repository.UsuarioRepository;
import com.integrador.empleau.service.EmpresaService;
import com.integrador.empleau.service.UsuarioServiceImpl;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

     private final EmpresaService empresaService;
    private final UsuarioRepository usuarioRepository;

    public EmpresaController(EmpresaService empresaService, UsuarioRepository usuarioRepository) {
        this.empresaService = empresaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/registroEmpresa")
    public String mostrarFormularioEmpresa(Model model) {
        Empresa empresa = new Empresa();
        empresa.setUsuario(new Usuario()); // Importante para evitar null
        model.addAttribute("empresa", empresa);
        return "registroEmpresa";
    }

    // Procesar el registro
    @PostMapping("/registrarEmpresa")
    public String procesarRegistroEmpresa(@ModelAttribute("empresa") Empresa empresa,
            RedirectAttributes redirectAttributes) {
        try {
            empresaService.registrarEmpresa(empresa);
            redirectAttributes.addFlashAttribute("success", "Empresa registrada exitosamente");
            return "redirect:/login"; // Redirige al login después del registro
        } catch (UsuarioServiceImpl.EmailAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("error", "Correo ya está registrado");
            return "redirect:/empresas/registroEmpresa";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error interno: " + e.getMessage());
            return "redirect:/empresas/registroEmpresa";
        }
    }

    // Mostrar perfil de la empresa
    @GetMapping("/perfilEmpresa")
    public String verPerfilEmpresa(Authentication authentication, Model model) {
        String email = authentication.getName(); // Email del usuario logueado
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        Empresa empresa = empresaService.obtenerPorEmail(email);
        if (empresa == null) {
            throw new UsernameNotFoundException("Empresa no encontrada para el usuario: " + email);
        }
        model.addAttribute("empresa", empresa);
        model.addAttribute("usuario", usuario);
        return "perfilEmpresa";
    }


       // Método para actualizar el perfil de la empresa
    @PostMapping("/actualizarPerfil")
    public String actualizarPerfilEmpresa(@ModelAttribute("empresa") Empresa empresaForm,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            String email = authentication.getName();
            Empresa empresaExistente = empresaService.obtenerPorEmail(email);
            // Mantener ID y otros campos no visibles del form
            empresaForm.setEmpresaId(empresaExistente.getEmpresaId());
            if (empresaForm.getUsuario() != null) {
                empresaForm.getUsuario().setUsuarioId(empresaExistente.getUsuario().getUsuarioId());
            }
            empresaService.actualizarEmpresa(empresaForm);
            redirectAttributes.addFlashAttribute("success", "Perfil actualizado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
        }
        return "redirect:/empresas/perfilEmpresa";
    }


}
