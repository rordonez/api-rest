package com.uma.informatica.controllers;

import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.controllers.beans.UpdateAlumnoBody;
import com.uma.informatica.controllers.resources.AlumnoResource;
import com.uma.informatica.controllers.resources.PfcResource;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.services.AlumnoService;
import com.uma.informatica.persistence.services.PfcService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by rafaordonez on 16/02/14.
 */

@ExposesResourceFor(Alumno.class)
@RestController
@Api(value = "alumnos", description = "Api para Alumnos")
@RequestMapping(value = "/alumnos")
public class AlumnoControllerRest  {

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


    @RequestMapping (method = RequestMethod.GET)
    public ResponseEntity<Resources<AlumnoResource>> getAlumnos() {
    	Resources<AlumnoResource> alumnosResources = new Resources<AlumnoResource>(alumnoResourceAssembler.toResources(alumnoService.getAll()));
    	alumnosResources.add(linkTo(methodOn(AlumnoControllerRest.class).getAlumnos()).withSelfRel());

        return new ResponseEntity<> (alumnosResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.POST)
    public ResponseEntity<Resources<AlumnoResource>> searchAlumnos(@Valid @RequestBody SearchAlumnoRequestBody search) {
        Resources<AlumnoResource> alumnosResources = new Resources<>(alumnoResourceAssembler.toResources(alumnoService.search(search.getDni(), search.getNombre(), search.getApellidos())));

        return new ResponseEntity<> (alumnosResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.PUT)
    public ResponseEntity<AlumnoResource> createAlumno(@Valid @RequestBody Alumno alumno) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.createAlumno(alumno.getDni(), alumno.getNombre(), alumno.getApellidos(), alumno.getTitulacion().name(), alumno.getDomicilio(), alumno.getLocalidad(), alumno.getPais(), alumno.getCodigoPostal(), alumno.getTelefono(), alumno.getEmail(), alumno.getFechaNacimiento())), HttpStatus.CREATED);
    }


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}")
    public ResponseEntity<AlumnoResource> getAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.findById(alumnoId)), HttpStatus.OK);
    }


    @RequestMapping (method = RequestMethod.PATCH, value = "/{alumnoId}")
    public ResponseEntity<AlumnoResource> updateAlumno(@PathVariable long alumnoId, @NotNull @Valid @RequestBody UpdateAlumnoBody alumno) {
        Alumno updatedAlumno = alumnoService.updateAlumno(alumnoId, alumno.getDomicilio(), alumno.getLocalidad(), alumno.getPais(), alumno.getCodigoPostal(), alumno.getEmail(), alumno.getTelefono());

        return new ResponseEntity<>(alumnoResourceAssembler.toResource(updatedAlumno), HttpStatus.ACCEPTED);
    }


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}")
    public ResponseEntity<AlumnoResource> removeAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.deleteAlumno(alumnoId)), HttpStatus.ACCEPTED);
    }


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/pfc/{pfcId}")
    public ResponseEntity<PfcResource> addPfc(@PathVariable long alumnoId, @PathVariable long pfcId) {
        PfcResource pfcResource = pfcResourceAssembler.toResource(pfcService.addPfcToAlumno(alumnoId, pfcId));
        return new ResponseEntity<>(pfcResource, HttpStatus.CREATED);
    }

    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}/pfc")
    public ResponseEntity<PfcResource> deletePfc(@PathVariable long alumnoId) {
        PfcResource pfcResource = pfcResourceAssembler.toResource(pfcService.deletePfcFromAlumno(alumnoId));
        return new ResponseEntity<>(pfcResource, HttpStatus.ACCEPTED);
    }
}
