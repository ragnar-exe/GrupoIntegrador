package com.integrador.empleau.service;

import com.integrador.empleau.model.Empresa;
import java.util.List;

public interface EmpresaService {

    Empresa registrarEmpresa(Empresa empresa);
    Empresa obtenerPorEmail(String email);
    Empresa actualizarEmpresa(Empresa empresa);

    Empresa obtenerEmpresaPorId(Integer id);
    void eliminarEmpresa(Integer id);
    List<Empresa> listarEmpresas();
}
