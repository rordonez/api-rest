package com.uma.informatica.core.exceptions;

/**
 * Created by rafa on 12/02/15.
 */
public class PfcSinDirectoresException extends RuntimeException {

    private Long pfcId;

    public PfcSinDirectoresException(Long pfcId) {
        super("Pfc con id: " + pfcId + " no tiene directores asignados");
        this.pfcId = pfcId;
    }

    public Long getPfcId() {
        return pfcId;
    }

    public void setPfcId(Long pfcId) {
        this.pfcId = pfcId;
    }
}
