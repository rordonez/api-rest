package com.uma.informatica.core.exceptions;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rafa on 11/02/15.
 */
public class CamposNoValidosEnFiltroDeBusquedaException extends RuntimeException {

    public CamposNoValidosEnFiltroDeBusquedaException() {
        super();
    }

    public CamposNoValidosEnFiltroDeBusquedaException(List<String> fields) {
        this("Los parámetros de búsqueda " + Arrays.toString(fields.toArray()) + "no son correctos");
    }

    public CamposNoValidosEnFiltroDeBusquedaException(String message) {
        super(message);
    }
}
