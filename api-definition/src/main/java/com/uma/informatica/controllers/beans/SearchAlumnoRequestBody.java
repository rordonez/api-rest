package com.uma.informatica.controllers.beans;

import java.io.Serializable;

/**
 * Created by rafa on 14/07/14.
 */
public class SearchAlumnoRequestBody implements Serializable {

    private String dni;
    private String nombre;
    private String apellidos;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
