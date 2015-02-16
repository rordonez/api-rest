package com.uma.informatica.core.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rafa on 08/07/14.
 */
public class ProfesorNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -2537754913402994422L;

	private List<Long> profesorsId;

    public ProfesorNoEncontradoException(long profesorId) {
        super("Profesor con identificador: " + profesorId + " no encontrado");
        profesorsId = new ArrayList<>();
        profesorsId.add(profesorId);
    }

    public ProfesorNoEncontradoException(List<Long> ids) {
        super("Profesores con ids: " + Arrays.toString(ids.toArray()) + " no encontrados");
        profesorsId = new ArrayList<>();
        profesorsId.addAll(ids);
    }

    public ProfesorNoEncontradoException() {
        super("Profesor no encontrado");
        this.profesorsId = new ArrayList<>();
    }

    public List<Long> getProfesorsId() {
        return profesorsId;
    }
}
