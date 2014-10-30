package com.uma.informatica.persistence.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa on 08/07/14.
 */
public class ProfesorNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -2537754913402994422L;

	private List<Long> profesorsId;

    public ProfesorNoEncontradoException(long message) {
        super("Profesor: " + message + "no encontrado");
        profesorsId = new ArrayList<>();
        profesorsId.add(message);
    }

    public ProfesorNoEncontradoException(List<Long> message) {
        super("Profesor: " + message + "no encontrado");
        profesorsId = new ArrayList<>();
        profesorsId.addAll(message);
    }

    public List<Long> getProfesorsId() {
        return profesorsId;
    }
}
