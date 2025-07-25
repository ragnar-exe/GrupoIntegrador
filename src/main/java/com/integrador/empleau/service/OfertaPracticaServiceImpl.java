package com.integrador.empleau.service;

import com.integrador.empleau.model.Empresa;
import com.integrador.empleau.model.Estado;
import com.integrador.empleau.model.OfertaPractica;
import com.integrador.empleau.repository.EmpresaRepository;
import com.integrador.empleau.repository.OfertaPracticaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfertaPracticaServiceImpl implements OfertaPracticaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private OfertaPracticaRepository ofertaRepository;

    // Implement methods for managing internship offers
    @Override
    public void crearOfertaPractica(OfertaPractica oferta) {

        if (oferta.getEmpresa() == null || oferta.getEmpresa().getEmpresaId() == null) {
            throw new IllegalArgumentException("La oferta debe estar asociada a una empresa válida");
        }

        // Verificar si la empresa existe en BD
        Empresa empresa = empresaRepository.findById(oferta.getEmpresa().getEmpresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada"));

        // Asignar la empresa existente a la oferta
        oferta.setEmpresa(empresa);

        // Limpiar texto
        oferta.setTitulo(oferta.getTitulo().trim());
        oferta.setDescripcion(oferta.getDescripcion().trim());
        oferta.setEstado(Estado.ABIERTA);
        oferta.setFechaPublicacion(java.time.LocalDate.now());

        // Guardar en la base de datos
        ofertaRepository.save(oferta);

    }

    @Override
    public OfertaPractica obtenerOfertaPorId(Integer id) {
        // Implementation here
        return null;
    }

    @Override
    public List<OfertaPractica> listarOfertas() {
        // Implementation here
        return null;
    }

    @Override
    public List<OfertaPractica> listarOfertasPorEmpresaId(Integer empresaId) {
        // Verificar si la empresa existe
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada"));
        // Obtener las ofertas asociadas a la empresa
        List<OfertaPractica> ofertas = ofertaRepository.findByEmpresa_EmpresaId(empresa.getEmpresaId());
        if (ofertas == null || ofertas.isEmpty())
            throw new EntityNotFoundException("No hay ofertas asociadas a esta empresa");
        // Retornar las ofertas de la empresa
        return ofertas;
    }

    @Override
    public OfertaPractica actualizarOfertaPractica(OfertaPractica ofertaActualizada) {
        OfertaPractica ofertaExistente = ofertaRepository.findById(ofertaActualizada.getIdOferta())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Oferta no encontrada con ID: " + ofertaActualizada.getIdOferta()));

        // 2. Actualiza campos de la Oferta
        ofertaExistente.setTitulo(ofertaActualizada.getTitulo());
        ofertaExistente.setDescripcion(ofertaActualizada.getDescripcion());
        ofertaExistente.setFechaLimite(ofertaActualizada.getFechaLimite());
        ofertaExistente.setEstado(ofertaActualizada.getEstado());

        // Guardar los cambios en la base de datos
        return ofertaRepository.save(ofertaExistente);
    }

    @Override
    public void eliminarOfertaPractica(Integer id) {
        OfertaPractica ofertaExistente = ofertaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Oferta no encontrada con ID: " + id));
        ofertaRepository.delete(ofertaExistente);
    }

    @Transactional
    @Override
    public void anularOfertaPractica(OfertaPractica oferta) {
        OfertaPractica ofertaExistente = ofertaRepository.findById(oferta.getIdOferta())
                .orElseThrow(() -> new EntityNotFoundException("Oferta no encontrada con ID: " + oferta.getIdOferta()));
        ofertaExistente.setEstado(Estado.ANULADA);
        ofertaRepository.save(ofertaExistente);
    }

}
