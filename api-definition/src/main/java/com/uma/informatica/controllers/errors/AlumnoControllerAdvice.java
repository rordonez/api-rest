package com.uma.informatica.controllers.errors;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.uma.informatica.persistence.exceptions.AlumnoNoEncontradoException;

@ControllerAdvice(annotations = RestController.class)
public class AlumnoControllerAdvice {
	
	private final MediaType vndErrorMediaType = MediaType.parseMediaType("application/vnd.error");
	
    @ExceptionHandler(AlumnoNoEncontradoException.class)
    ResponseEntity<VndErrors> userNotFoundException(AlumnoNoEncontradoException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getAlumnoId() + "");
    }
    
    private <E extends Exception> ResponseEntity<VndErrors> error(E e, HttpStatus httpStatus, String logref) {
        String msg = e.getMessage()!= null ? e.getMessage() : e.getClass().getSimpleName();   
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(this.vndErrorMediaType);
        return new ResponseEntity<>(new VndErrors(logref, msg), httpHeaders, httpStatus);
    }

}
