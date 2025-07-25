package com.integrador.empleau.controller;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.integrador.empleau.model.Alumno;
import com.integrador.empleau.model.Usuario;
import com.integrador.empleau.repository.AlumnoRepository;
import com.integrador.empleau.repository.UsuarioRepository;
import com.integrador.empleau.service.AlumnoService;
import com.integrador.empleau.service.UsuarioServiceImpl;

import lombok.AllArgsConstructor;

import com.integrador.empleau.service.EmpresaService;
import com.integrador.empleau.service.OfertaPracticaService;
import org.springframework.ui.Model;
import com.integrador.empleau.model.Empresa;
import com.integrador.empleau.model.OfertaPractica;
import com.integrador.empleau.model.Postulacion;
import com.integrador.empleau.service.PostulacionService;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/alumnos")
@AllArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;
    private final AlumnoRepository alumnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaService empresaService;
    private final OfertaPracticaService ofertaService;
    private final PostulacionService postulacionService;

    // Mostrar formulario de registro
    @GetMapping("/registroAlumno")
    public String mostrarFormularioRegistroAlumno(Model model) {
        Alumno alumno = new Alumno();
        alumno.setUsuario(new Usuario()); // Necesario para evitar null en el formulario
        model.addAttribute("alumno", alumno);
        return "registroAlumno";
    }

    // Procesar el registro
    @PostMapping("/registrarAlumno")
    public String procesarRegistroAlumno(@ModelAttribute("alumno") Alumno alumno,
            RedirectAttributes redirectAttributes) {
        try {
            alumnoService.registrarAlumno(alumno);
            redirectAttributes.addFlashAttribute("success", "Alumno registrado exitosamente");
            return "redirect:/login"; // Redirige al login después del registro
        } catch (UsuarioServiceImpl.EmailAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("error", "Correo ya está registrado");
            return "redirect:/alumnos/registroAlumno";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error interno: " + e.getMessage());
            return "redirect:/alumnos/registroAlumno";
        }
    }

    // Mostrar perfil del alumno
    @GetMapping("/perfilAlumno")
    public String verPerfilAlumno(Authentication authentication, Model model) {
        String email = authentication.getName(); // Email del usuario logueado
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        Alumno alumno = alumnoRepository.findByUsuario(usuario);

        model.addAttribute("alumno", alumno);
        model.addAttribute("usuario", usuario);
        return "perfilAlumno";
    }

    // Método para actualizar el perfil del alumno
    @PostMapping("/actualizarPerfil")
    public String actualizarPerfilAlumno(@ModelAttribute("alumno") Alumno alumnoForm,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            String email = authentication.getName();
            Alumno alumnoExistente = alumnoService.obtenerPorEmail(email);
            // Mantener ID y otros campos no visibles del form
            alumnoForm.setAlumnoId(alumnoExistente.getAlumnoId());
            if (alumnoForm.getUsuario() != null) {
                alumnoForm.getUsuario().setUsuarioId(alumnoExistente.getUsuario().getUsuarioId());
            }
            alumnoService.actualizarAlumno(alumnoForm);
            redirectAttributes.addFlashAttribute("success", "Perfil actualizado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
        }
        return "redirect:/alumnos/perfilAlumno";
    }

    @GetMapping("/listarEmpresas")
    public String listarEmpresas(Model model) {
        List<Empresa> empresas = empresaService.listarEmpresas();
        model.addAttribute("empresas", empresas);
        model.addAttribute("empresa", new Empresa());
        return "empresasAlumno";
    }

    @GetMapping("/ofertasEmpresa/{empresaId}")
    public String listarOfertasPorEmpresaParaAlumno(@PathVariable Integer empresaId, Model model) {
        List<OfertaPractica> ofertas = ofertaService.listarOfertasPorEmpresaId(empresaId);
        model.addAttribute("ofertas", ofertas);
        return "ofertasAlumno"; // este será el template donde se muestran las ofertas
    }

    @PostMapping("/postular")
    public String postularOferta(@RequestParam("ofertaId") Integer ofertaId, Principal principal,
            RedirectAttributes redirectAttributes) {
        String emailAlumno = principal.getName();

        try {
            postulacionService.postularAlumnoAOferta(emailAlumno, ofertaId);
            redirectAttributes.addFlashAttribute("success", "Te has postulado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/alumnos/postulaciones"; // o redirige a donde prefieras
    }

    @GetMapping("/postulaciones")
    public String verMisPostulaciones(Model model, Principal principal) {
        String email = principal.getName();

        // Buscar usuario y alumno
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        Alumno alumno = alumnoRepository.findByUsuario(usuario);
        if (alumno == null) {
            throw new IllegalArgumentException("Alumno no encontrado");
        }

        // Obtener postulaciones
        List<Postulacion> postulaciones = postulacionService.obtenerPorAlumnoId(alumno.getAlumnoId());

        model.addAttribute("postulaciones", postulaciones);
        return "postulacionesAlumno"; // Vista donde se listan las postulaciones
    }

}
