package com.uma.informatica.errors;

import com.uma.informatica.core.exceptions.*;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandlerControllerAdvice extends ResponseEntityExceptionHandler{
	
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

    @ExceptionHandler(PfcSinDirectoresException.class)
    ResponseEntity<VndErrors> pfcSinDirectoresException(PfcSinDirectoresException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
        }

        headers.setContentType(this.vndErrorMediaType);
        return new ResponseEntity<Object>(new VndErrors("1", ex.getMessage()), headers, status);
    }

    private <E extends Exception> ResponseEntity<VndErrors> error(E e, HttpStatus httpStatus, String logref) {
        String msg = e.getMessage()!= null ? e.getMessage() : e.getClass().getSimpleName();   
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(this.vndErrorMediaType);
        return new ResponseEntity<>(new VndErrors(logref, msg), httpHeaders, httpStatus);
    }

}
