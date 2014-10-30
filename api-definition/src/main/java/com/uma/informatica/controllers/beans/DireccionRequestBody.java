package com.uma.informatica.controllers.beans;

import java.io.Serializable;

/**
 * Created by rafa on 14/07/14.
 */
public class DireccionRequestBody implements Serializable {

	private static final long serialVersionUID = 2478321512997365280L;

	private String domicilio;
    private String localidad;
    private String pais;
    private String codigoPostal;

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
