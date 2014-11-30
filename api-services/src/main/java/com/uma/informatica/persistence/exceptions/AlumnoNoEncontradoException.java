package com.uma.informatica.persistence.exceptions;

/**
 * Created by rafaordonez on 25/02/14.
 */
public class AlumnoNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -3542924270370931418L;

    public AlumnoNoEncontradoException() {
        super("No se encontró ningún alumno");
    }

    public AlumnoNoEncontradoException(String msg) {
        super(msg);
    }

	public AlumnoNoEncontradoException(long alumnoId) {
        super("No se encontró ningún alumno con id: " + alumnoId);
        this.alumnoId = alumnoId;
    }

    private Long alumnoId;

    public Long getAlumnoId() {
        return alumnoId;
    }

}
