package com.uma.informatica.core.exceptions;

/**
 * Created by rafa on 12/02/15.
 */
public class DirectorAcademicoNotFoundException extends RuntimeException {

    public DirectorAcademicoNotFoundException(long pfcId) {
        this("No se ha podido encontrar el directorAcademico del Pfc con id:" + pfcId);
    }

    public DirectorAcademicoNotFoundException(String message) {
        super(message);
    }
}
