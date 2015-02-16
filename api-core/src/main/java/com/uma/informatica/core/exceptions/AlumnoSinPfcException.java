package com.uma.informatica.core.exceptions;

/**
 * Created by rafa on 14/07/14.
 */
public class AlumnoSinPfcException extends RuntimeException {

	private static final long serialVersionUID = -8110431524359273613L;

	private Long alumnoId;

    public AlumnoSinPfcException(Long alumnoId) {
        super("Alumno: " + alumnoId + " no tiene Pfc asignado");
        this.alumnoId = alumnoId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }
}
