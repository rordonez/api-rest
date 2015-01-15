package com.uma.informatica.persistence.exceptions;

/**
 * Created by rafaordonez on 26/02/14.
 */
public class PfcNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 8478840023357207797L;

	private Long pfcId;

    public PfcNoEncontradoException() {
        this("No se encontró ningún pfc");
    }

    public PfcNoEncontradoException(String message) {
        super(message);
    }

    public PfcNoEncontradoException(Long pfcId) {
        super("No se encontró ningún pfc con id: " + pfcId);
        this.pfcId = pfcId;
    }

    public Long getPfcId() {
        return pfcId;
    }
}
