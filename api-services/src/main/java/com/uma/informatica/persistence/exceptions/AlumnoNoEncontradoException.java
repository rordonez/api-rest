package com.uma.informatica.persistence.exceptions;

/**
 * Created by rafaordonez on 25/02/14.
 */
public class AlumnoNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -3542924270370931418L;

	public AlumnoNoEncontradoException(long alumnoId) {
        super("alumno#" + alumnoId + " was not found");
        this.alumnoId = alumnoId;
    }

    private long alumnoId;

    public long getAlumnoId() {
        return alumnoId;
    }

}
