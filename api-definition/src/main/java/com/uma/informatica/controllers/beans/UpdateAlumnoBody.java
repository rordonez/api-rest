package com.uma.informatica.controllers.beans;

/**
 * Created by rafa on 30/11/14.
 */
public class UpdateAlumnoBody {

    private DireccionRequestBody direccion;

    private String email;

    private String telefono;

    public DireccionRequestBody getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionRequestBody direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
