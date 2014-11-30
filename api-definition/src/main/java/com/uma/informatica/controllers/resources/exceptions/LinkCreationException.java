package com.uma.informatica.controllers.resources.exceptions;

/**
 * Created by rafa on 30/11/14.
 */
public class LinkCreationException extends RuntimeException {

    public LinkCreationException() {
        super();
    }

    public LinkCreationException(String message) {
        super(message);
    }
}
