package com.uma.informatica.errors;

import com.uma.informatica.core.exceptions.*;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(annotations = RestController.class)
public class ErrorHandlerControllerAdvice extends ResponseEntityExceptionHandler {
	
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

    @ExceptionHandler(DirectorAcademicoNotFoundException.class)
    ResponseEntity<VndErrors> directorAcademicoNotFound(DirectorAcademicoNotFoundException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ProfesorNoEncontradoException.class)
    ResponseEntity<VndErrors> profesorNotFound(ProfesorNoEncontradoException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AlumnoSinPfcException.class)
    ResponseEntity<VndErrors> alumnoSinPfcException(AlumnoSinPfcException e) {
        String logRef = e.getAlumnoId().toString();
        return error(e, HttpStatus.NOT_FOUND, logRef);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<VndErrors> argumentNotValidException(MethodArgumentNotValidException e) {
        return error(e, HttpStatus.BAD_REQUEST, "1", e.getBindingResult().getFieldErrors());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<VndErrors> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return error(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(PfcSinDirectoresException.class)
    ResponseEntity<VndErrors> pfcSinDirectoresException(PfcSinDirectoresException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getMessage());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<VndErrors> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return error(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }



    
    private <E extends Exception> ResponseEntity<VndErrors> error(E e, HttpStatus httpStatus, String logref) {
        String msg = e.getMessage()!= null ? e.getMessage() : e.getClass().getSimpleName();   
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(this.vndErrorMediaType);
        return new ResponseEntity<>(new VndErrors(logref, msg), httpHeaders, httpStatus);
    }

    private <E extends Exception> ResponseEntity<VndErrors> error(E e, HttpStatus httpStatus, String logref, List<FieldError> errors) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(this.vndErrorMediaType);

        List<VndErrors.VndError> errorsFound = new ArrayList<>();
        for (FieldError error : errors) {
            errorsFound.add(new VndErrors.VndError(logref, "Parámetro: " + error.getField() + " " + error.getDefaultMessage()));
        }
        VndErrors vndErrors = new VndErrors(errorsFound);

        return new ResponseEntity<>(vndErrors, httpHeaders, httpStatus);
    }

}
