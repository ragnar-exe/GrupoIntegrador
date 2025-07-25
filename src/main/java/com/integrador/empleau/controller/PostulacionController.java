package com.integrador.empleau.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.integrador.empleau.model.Postulacion;
import com.integrador.empleau.service.PostulacionService;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/postulaciones")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    @GetMapping("/oferta/{id}/postulantes")
    public String verPostulantes(@PathVariable("id") Integer idOferta, Model model) {
        List<Postulacion> postulaciones = postulacionService.obtenerPostulacionesPorOfertaId(idOferta);
        model.addAttribute("postulaciones", postulaciones);
        return "postulacionesEmpresa";
    }

    @PostMapping("/aceptar")
    public String aceptarPostulacion(@RequestParam("idPostulacion") Integer idPostulacion,
            @RequestParam("idOferta") Integer idOferta,
            RedirectAttributes redirectAttributes) {
        postulacionService.actualizarEstadoPostulacion(idPostulacion, "ACEPTADA");
        redirectAttributes.addFlashAttribute("success", "Postulación aceptada.");
        return "redirect:/postulaciones/oferta/" + idOferta + "/postulantes"; // Redirige a la oferta específica
    }

    @PostMapping("/rechazar")
    public String rechazarPostulacion(@RequestParam("idPostulacion") Integer idPostulacion,
            @RequestParam("idOferta") Integer idOferta,
            RedirectAttributes redirectAttributes) {
        postulacionService.actualizarEstadoPostulacion(idPostulacion, "RECHAZADA");
        redirectAttributes.addFlashAttribute("success", "Postulación rechazada.");
        return "redirect:/postulaciones/oferta/" + idOferta + "/postulantes";
    }

}
