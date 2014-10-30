package com.uma.informatica.persistence.exceptions;

/**
 * Created by rafa on 07/07/14.
 */
public class InvalidPfcDataCreationException extends RuntimeException {

	private static final long serialVersionUID = 6730879148792638718L;

	private String message;

    public InvalidPfcDataCreationException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
