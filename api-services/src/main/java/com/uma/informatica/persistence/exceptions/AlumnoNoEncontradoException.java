package com.uma.informatica.persistence.exceptions;

/**
 * Created by rafaordonez on 25/02/14.
 */
public class AlumnoNoEncontradoException extends RuntimeException {

    public AlumnoNoEncontradoException(Long alumnoId) {
        super("alumno#" + alumnoId + " was not found");
        this.alumnoId = alumnoId;
    }

    private long alumnoId;

    public long getAlumnoId() {
        return alumnoId;
    }

}
