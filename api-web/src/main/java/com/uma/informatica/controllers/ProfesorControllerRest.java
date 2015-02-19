package com.uma.informatica.controllers;

import com.uma.informatica.controllers.assemblers.ProfesorResourceAssembler;
import com.uma.informatica.controllers.resources.ProfesorResource;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.uma.informatica.controllers.utils.ControllerUtils.allows;

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
    public ResponseEntity<PagedResources<ProfesorResource>> getProfesores(@PageableDefault(size = 10) Pageable pageable, PagedResourcesAssembler pagedAssembler) {
        PagedResources<ProfesorResource> profesorResources = pagedAssembler.toResource(profesorService.getAll(pageable), profesorResourceAssembler);

        return new ResponseEntity<>(profesorResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.GET, params = {"ids"})
    public ResponseEntity<Resources<ProfesorResource>> getProfesoresByIds(@PageableDefault(size = 10) Pageable pageable, PagedResourcesAssembler pagedAssembler, @RequestParam("ids") List<Long> ids) {
        Resources<ProfesorResource> profesorResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(profesorService.getAll(ids)));

        return new ResponseEntity<>(profesorResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.POST)
    public ResponseEntity<ProfesorResource> createProfesor(@Valid @RequestBody Profesor profesor) {
        return new ResponseEntity<>(profesorResourceAssembler.toResource(profesorService.createProfesor(profesor.getDni(), profesor.getNombre(), profesor.getApellidos(), profesor.getTitulacion(), profesor.getTelefono(), profesor.getEmail())),HttpStatus.CREATED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> profesoresOptions() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }


    @RequestMapping (method = RequestMethod.GET, value = "/{profesorId}")
    public ResponseEntity<ProfesorResource> getProfesor(@PathVariable long profesorId) {

        return new ResponseEntity<>(profesorResourceAssembler.toResource(profesorService.findById(profesorId)), HttpStatus.OK);
    }


    @RequestMapping (method = RequestMethod.DELETE, value = "/{profesorId}")
    public ResponseEntity<ProfesorResource> removeProfesor(@PathVariable long profesorId) {
        return new ResponseEntity<>(profesorResourceAssembler.toResource(profesorService.deleteProfesor(profesorId)), HttpStatus.ACCEPTED);
    }


    @RequestMapping (method = RequestMethod.PATCH, value = "/{profesorId}")
    public ResponseEntity<ProfesorResource> updateProfesor(@PathVariable long profesorId, @RequestBody Profesor profesor) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS, value = "/{profesorId}")
    public ResponseEntity<Void> profesorOptions() {
        return allows(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.OPTIONS);
    }

}
