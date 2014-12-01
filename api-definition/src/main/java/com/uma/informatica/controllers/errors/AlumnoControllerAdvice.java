package com.uma.informatica.controllers.errors;

import com.uma.informatica.persistence.exceptions.AlumnoNoEncontradoException;
import com.uma.informatica.persistence.exceptions.AlumnoSinPfcException;
import com.uma.informatica.persistence.exceptions.PfcNoEncontradoException;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(annotations = RestController.class)
public class AlumnoControllerAdvice {
	
	private final MediaType vndErrorMediaType = MediaType.parseMediaType("application/vnd.error+json;charset=UTF-8");
	
    @ExceptionHandler(AlumnoNoEncontradoException.class)
    ResponseEntity<VndErrors> userNotFoundException(AlumnoNoEncontradoException e) {
        String logRef = e.getAlumnoId() != null ? e.getAlumnoId().toString() : "0";
        return error(e, HttpStatus.NOT_FOUND, logRef);
    }

    @ExceptionHandler(PfcNoEncontradoException.class)
    ResponseEntity<VndErrors> userNotFoundException(PfcNoEncontradoException e) {
        String logRef = e.getPfcId() != null ? e.getPfcId().toString() : "0";
        return error(e, HttpStatus.NOT_FOUND, logRef);
    }

    @ExceptionHandler(AlumnoSinPfcException.class)
    ResponseEntity<VndErrors> alumnoSinPfcException(AlumnoSinPfcException e) {
        String logRef = e.getAlumnoId().toString();
        return error(e, HttpStatus.NOT_FOUND, logRef);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Resources<VndErrors>> argumentNotValidException(MethodArgumentNotValidException e) {
        return error(e, HttpStatus.BAD_REQUEST, "1", e.getBindingResult().getFieldErrors());
    }

    
    private <E extends Exception> ResponseEntity<VndErrors> error(E e, HttpStatus httpStatus, String logref) {
        String msg = e.getMessage()!= null ? e.getMessage() : e.getClass().getSimpleName();   
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(this.vndErrorMediaType);
        return new ResponseEntity<>(new VndErrors(logref, msg), httpHeaders, httpStatus);
    }

    private <E extends Exception> ResponseEntity<Resources<VndErrors>> error(E e, HttpStatus httpStatus, String logref, List<FieldError> errors) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(this.vndErrorMediaType);

        List<VndErrors> vndErrors = new ArrayList<>();
        for (FieldError error : errors) {
            vndErrors.add(new VndErrors(logref, "Parámetro: " + error.getField() + " " + error.getDefaultMessage()));
        }

        return new ResponseEntity<>(new Resources<>(vndErrors), httpHeaders, httpStatus);
    }

}
