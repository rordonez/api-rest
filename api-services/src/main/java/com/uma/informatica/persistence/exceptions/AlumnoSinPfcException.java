package com.uma.informatica.persistence.exceptions;

/**
 * Created by rafa on 14/07/14.
 */
public class AlumnoSinPfcException extends RuntimeException {

    private long alumnoId;

    public AlumnoSinPfcException(long alumnoId) {
        super("Alumno: " + alumnoId + " no tiene Pfc asignado");
        this.alumnoId = alumnoId;
    }

    public long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(long alumnoId) {
        this.alumnoId = alumnoId;
    }
}
