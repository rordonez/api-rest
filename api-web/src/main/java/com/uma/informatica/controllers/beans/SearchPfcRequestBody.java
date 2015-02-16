package com.uma.informatica.controllers.beans;

import com.uma.informatica.persistence.models.enums.EstadoPfc;

/**
 * Created by rafa on 01/12/14.
 */
public class SearchPfcRequestBody {
    private String departamento;
    private String nombre;
    private EstadoPfc estado;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadoPfc getEstado() {
        return estado;
    }

    public void setEstado(EstadoPfc estado) {
        this.estado = estado;
    }
}
