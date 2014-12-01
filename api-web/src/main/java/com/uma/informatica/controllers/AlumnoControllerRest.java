package com.uma.informatica.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.uma.informatica.controllers.beans.UpdateAlumnoBody;
import com.uma.informatica.persistence.exceptions.AlumnoNoEncontradoException;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uma.informatica.controllers.beans.DireccionRequestBody;
import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.controllers.resources.AlumnoResourceAssembler;
import com.uma.informatica.controllers.resources.PfcResourceAssembler;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.services.AlumnoService;
import com.uma.informatica.persistence.services.PfcService;
import com.uma.informatica.resources.AlumnoResource;
import com.uma.informatica.resources.PfcResource;

/**
 * Created by rafaordonez on 16/02/14.
 */
@ExposesResourceFor(Alumno.class)
@RestController
public class AlumnoControllerRest implements AlumnoController {

    private AlumnoService alumnoService;
    private PfcService pfcService;

    private final AlumnoResourceAssembler alumnoResourceAssembler;
    private final PfcResourceAssembler pfcResourceAssembler;

    @Inject
    public AlumnoControllerRest(AlumnoService alumnoService, PfcService pfcService, AlumnoResourceAssembler alumnoResourceAssembler, PfcResourceAssembler pfcResourceAssembler) {
        this.alumnoService = alumnoService;
        this.pfcService = pfcService;
        this.alumnoResourceAssembler = alumnoResourceAssembler;
        this.pfcResourceAssembler = pfcResourceAssembler;
    }


    @Override
    public ResponseEntity<Resources<AlumnoResource>> getAlumnos() {
    	Resources<AlumnoResource> alumnosResources = new Resources<AlumnoResource>(alumnoResourceAssembler.toResources(alumnoService.getAll()));
    	alumnosResources.add(linkTo(methodOn(AlumnoControllerRest.class).getAlumnos()).withSelfRel());

        return new ResponseEntity<> (alumnosResources, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resources<AlumnoResource>> searchAlumnos(@Valid @RequestBody SearchAlumnoRequestBody search) {

        Resources<AlumnoResource> alumnosResources = new Resources<AlumnoResource>(alumnoResourceAssembler.toResources(alumnoService.search(search.getDni(), search.getNombre(), search.getApellidos())));

        return new ResponseEntity<> (alumnosResources, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlumnoResource> createAlumno(@Valid @RequestBody Alumno alumno) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.createAlumno(alumno.getDni(), alumno.getNombre(), alumno.getApellidos(), alumno.getTitulacion().name(), alumno.getDomicilio(), alumno.getLocalidad(), alumno.getPais(), alumno.getCodigoPostal(), alumno.getTelefono(), alumno.getEmail(), alumno.getFechaNacimiento())), HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<AlumnoResource> getAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.findById(alumnoId)), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<AlumnoResource> updateAlumno(@PathVariable long alumnoId, @NotNull @Valid @RequestBody UpdateAlumnoBody alumno) {
        Alumno updatedAlumno = null;
        if(alumno.getDireccion() != null) {
            updatedAlumno = alumnoService.updateDireccion(alumnoId, alumno.getDireccion().getDomicilio(), alumno.getDireccion().getLocalidad(), alumno.getDireccion().getPais(), alumno.getDireccion().getCodigoPostal());
        }
        if(alumno.getEmail() != null) {
            updatedAlumno = alumnoService.updateEmail(alumnoId, alumno.getEmail());
        }
        if(alumno.getTelefono() != null) {
            updatedAlumno = alumnoService.updateTelefono(alumnoId, alumno.getTelefono());
        }
        if(updatedAlumno == null) {
            throw new AlumnoNoEncontradoException("No se ha podido actualizar la información del alumno");
        }

        return new ResponseEntity<>(alumnoResourceAssembler.toResource(updatedAlumno), HttpStatus.ACCEPTED);
    }


    @Override
    public ResponseEntity<AlumnoResource> removeAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.deleteAlumno(alumnoId)), HttpStatus.ACCEPTED);
    }



    @Override
    public ResponseEntity<PfcResource> getPfc(@PathVariable long alumnoId) {
        PfcResource pfcResource = pfcResourceAssembler.toResource(pfcService.getPfcFromAlumno(alumnoId));
        pfcResource.add(linkTo(methodOn(this.getClass()).getAlumno(alumnoId)).withRel("alumno"));
        return new ResponseEntity<>(pfcResource, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PfcResource> addPfc(@PathVariable long alumnoId, @PathVariable long pfcId) {
        PfcResource pfcResource = pfcResourceAssembler.toResource(pfcService.addPfcToAlumno(alumnoId, pfcId));
        pfcResource.add(linkTo(methodOn(getClass()).getAlumno(alumnoId)).withRel("alumno"));
        return new ResponseEntity<>(pfcResource, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PfcResource> deletePfc(@PathVariable long alumnoId) {
        PfcResource pfcResource = pfcResourceAssembler.toResource(pfcService.deletePfcFromAlumno(alumnoId));
        pfcResource.add(linkTo(methodOn(this.getClass()).getAlumno(alumnoId)).withRel("alumno"));
        return new ResponseEntity<>(pfcResource, HttpStatus.ACCEPTED);
    }
}
