package com.uma.informatica.persistence.exceptions;

/**
 * Created by rafa on 07/07/14.
 */
public class InvalidPfcDataCreationException extends RuntimeException {

    private String message;

    public InvalidPfcDataCreationException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
