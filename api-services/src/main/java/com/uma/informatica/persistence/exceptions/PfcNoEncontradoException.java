package com.uma.informatica.persistence.exceptions;

/**
 * Created by rafaordonez on 26/02/14.
 */
public class PfcNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 8478840023357207797L;

	private long pfcId;

    public PfcNoEncontradoException(Long pfcId) {
        super("pfc#" + pfcId + " was not found");
        this.pfcId = pfcId;
    }

    public long getPfcId() {
        return pfcId;
    }
}
