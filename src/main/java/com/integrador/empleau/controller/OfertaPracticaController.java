package com.integrador.empleau.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.integrador.empleau.service.OfertaPracticaService;

import lombok.AllArgsConstructor;

import com.integrador.empleau.repository.EmpresaRepository;

import java.security.Principal;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.integrador.empleau.model.Empresa;
import com.integrador.empleau.model.OfertaPractica;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/ofertas")
@AllArgsConstructor
public class OfertaPracticaController {

    private final OfertaPracticaService ofertaService;
    private final EmpresaRepository empresaRepository;

   

    @GetMapping("/registroOferta")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("oferta", new OfertaPractica());
        return "ofertasEmpresa"; // nombre de tu vista HTML para el registro
    }

    @PostMapping("/registrarOferta")
    public String registrarOferta(@ModelAttribute OfertaPractica oferta, Principal principal,
            RedirectAttributes redirectAttributes) {
        try {
            String email = principal.getName();
            Empresa empresa = empresaRepository.findByUsuarioEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));
            oferta.setEmpresa(empresa);
            ofertaService.crearOfertaPractica(oferta);

            redirectAttributes.addFlashAttribute("success", "Oferta registrada correctamente");
            return "redirect:/ofertas/ListarOfertas";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al registrar la oferta: " + e.getMessage());
        }
        return "redirect:/ofertas/registroOferta"; // redirigir de nuevo al formulario en caso de error
    }

    @GetMapping("/ListarOfertas")
    public String listarOfertasPorEmpresa(Model model, Principal principal) {
        String email = principal.getName(); // obtiene el email del usuario logueado
        Empresa empresa = empresaRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));

        List<OfertaPractica> ofertas = ofertaService.listarOfertasPorEmpresaId(empresa.getEmpresaId());
        model.addAttribute("ofertas", ofertas);
        model.addAttribute("oferta", new OfertaPractica());
        return "ofertasEmpresa";
    }

    @PostMapping("/actualizarOferta")
    public String actualizarOferta(@ModelAttribute OfertaPractica oferta, Principal principal,
            RedirectAttributes redirectAttributes) {
        try {
            String email = principal.getName();
            Empresa empresa = empresaRepository.findByUsuarioEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));
            OfertaPractica ofertaExistente = ofertaService.obtenerOfertaPorId(oferta.getIdOferta());
            oferta.setEstado(ofertaExistente.getEstado());
            // Asegurarse de que la oferta sigue asociada a la empresa
            oferta.setEmpresa(empresa);
            ofertaService.actualizarOfertaPractica(oferta);

            redirectAttributes.addFlashAttribute("success", "Oferta actualizada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la oferta: " + e.getMessage());
        }

        return "redirect:/ofertas/ListarOfertas";
    }

    @PostMapping("/anularOferta/{id}")
    public String anularOferta(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            OfertaPractica oferta = ofertaService.obtenerOfertaPorId(id);
            ofertaService.anularOfertaPractica(oferta);
            redirectAttributes.addFlashAttribute("success", "Oferta anulada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al anular la oferta: " + e.getMessage());
        }
        return "redirect:/ofertas/ListarOfertas";
    }


}
