package com.uma.informatica.controllers;

import com.uma.informatica.controllers.assemblers.ProfesorResourceAssembler;
import com.uma.informatica.controllers.resources.ProfesorResource;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by rafaordonez on 02/03/14.
 */
@ExposesResourceFor(Profesor.class)
@RestController
@RequestMapping(value = "/profesores")
public class ProfesorControllerRest {

    private ProfesorService profesorService;

    private final ProfesorResourceAssembler profesorResourceAssembler;

    @Autowired
    public ProfesorControllerRest(ProfesorService profesorService, ProfesorResourceAssembler profesorResourceAssembler) {
        this.profesorService = profesorService;
        this.profesorResourceAssembler = profesorResourceAssembler;
    }

    @RequestMapping (method = RequestMethod.GET)
    public ResponseEntity<Resources<ProfesorResource>> getProfesores() {
        Resources<ProfesorResource> profesorResources = new Resources<>(profesorResourceAssembler.toResources(profesorService.getAll()));
        profesorResources.add(linkTo(methodOn(ProfesorControllerRest.class).getProfesores()).withSelfRel());

        return new ResponseEntity<>(profesorResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.GET, value = "/{profesorId}")
    public ResponseEntity<ProfesorResource> getProfesor(@PathVariable long profesorId) {

        return new ResponseEntity<>(profesorResourceAssembler.toResource(profesorService.findById(profesorId)), HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.POST)
    public ResponseEntity<ProfesorResource> createProfesor(@Valid @RequestBody Profesor profesor) {
        return new ResponseEntity<>(profesorResourceAssembler.toResource(profesorService.createProfesor(profesor.getDni(), profesor.getNombre(), profesor.getApellidos(), profesor.getTitulacion(), profesor.getTelefono(), profesor.getEmail())),HttpStatus.CREATED);
    }

    @RequestMapping (method = RequestMethod.DELETE, value = "/{profesorId}")
    public ResponseEntity<ProfesorResource> removeProfesor(@PathVariable long profesorId) {
        return new ResponseEntity<>(profesorResourceAssembler.toResource(profesorService.deleteProfesor(profesorId)), HttpStatus.ACCEPTED);
    }


    @RequestMapping (method = RequestMethod.PATCH, value = "/{profesorId}")
    public ResponseEntity<ProfesorResource> updateProfesor(@PathVariable long profesorId, @RequestBody Profesor profesor) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
