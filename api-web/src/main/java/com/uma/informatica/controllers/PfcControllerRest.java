package com.uma.informatica.controllers;

import com.uma.informatica.controllers.resources.PfcResourceAssembler;
import com.uma.informatica.controllers.resources.ProfesorResourceAssembler;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.persistence.services.PfcService;
import com.uma.informatica.resources.PfcResource;
import com.uma.informatica.resources.ProfesorResource;
import com.wordnik.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by rafaordonez on 26/02/14.
 */

@ExposesResourceFor(Pfc.class)
@RestController
@Api(value = "pfcs", description = "Api para PFC")
@RequestMapping(value = "/pfcs", produces = MediaType.APPLICATION_JSON_VALUE)
public class PfcControllerRest {

    private PfcService pfcService;

    private final PfcResourceAssembler pfcResourceAssembler;
    private final ProfesorResourceAssembler profesorResourceAssembler;
    
    @Inject
    PfcControllerRest(PfcService pfcService, PfcResourceAssembler pfcResourceAssembler, ProfesorResourceAssembler profesorResourceAssembler) {
        this.pfcService = pfcService;
        this.pfcResourceAssembler = pfcResourceAssembler;
        this.profesorResourceAssembler = profesorResourceAssembler;
    }

    @RequestMapping (method = RequestMethod.GET)
    public ResponseEntity<Resources<PfcResource>> getPfcs() {
    	Resources<PfcResource> pfcsResources = new Resources<PfcResource>(pfcResourceAssembler.toResources(pfcService.getAll()));
    	pfcsResources.add(linkTo(methodOn(PfcControllerRest.class).getPfcs()).withSelfRel());
    	
        return new ResponseEntity<> (pfcsResources, HttpStatus.OK);
    }


    @RequestMapping (method = RequestMethod.GET, params = {"search=true"})
    public ResponseEntity<Resources<PfcResource>> searchPfcs(@RequestParam(required = false) String departamento, @RequestParam(required = false) String nombre, @RequestParam(required = false) EstadoPfc estado) {
    	Resources<PfcResource> pfcsResources = new Resources<>(pfcResourceAssembler.toResources(pfcService.search(departamento, nombre, estado != null ? estado.name() : null)));
        return new ResponseEntity<> (pfcsResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.PUT)
    public ResponseEntity<PfcResource> createPfc(@Valid @RequestBody Pfc pfc) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.createPfc(pfc.getNombre(), pfc.getDepartamento(), pfc.getEstado(), new ArrayList<Long>())), HttpStatus.CREATED);
    }

    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}")
    public ResponseEntity<PfcResource> getPfc(@PathVariable long pfcId) {
    	return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.findById(pfcId)), HttpStatus.OK);
    }


    @RequestMapping (method = RequestMethod.DELETE, value = "/{pfcId}")
    public ResponseEntity<PfcResource> removePfc(@PathVariable long pfcId) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.deletePfc(pfcId)), HttpStatus.ACCEPTED);
    }

    @RequestMapping (method = RequestMethod.POST, value = "/{pfcId}/nombre")
    public ResponseEntity<PfcResource> updateNombre(@PathVariable long pfcId, @RequestParam String nombre) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.updateNombre(pfcId, nombre)),HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.POST, value = "/{pfcId}/estado")
    public ResponseEntity<PfcResource> updateEstado(@PathVariable long pfcId, @RequestParam EstadoPfc estado) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.updateEstado(pfcId, estado)), HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}/directores_academicos")
    public ResponseEntity<ProfesorResource> getDirectorAcademico(@PathVariable long pfcId) {
        return new ResponseEntity<>(profesorResourceAssembler.toResource(pfcService.findByDirectorAcademico(pfcId)), HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.POST, value = "/{pfcId}/fechafin")
    public ResponseEntity<PfcResource> updateFechaFin(@PathVariable long pfcId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.updateFechaFin(pfcId, fechaFin)), HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.PUT, value = "/{pfcId}/directores_academicos")
    public ResponseEntity<ProfesorResource> changeDirectorAcademico(@PathVariable long pfcId, @RequestParam long profesor) {
        return new ResponseEntity<>(profesorResourceAssembler.toResource(pfcService.changeDirectorAcademico(pfcId, profesor)), HttpStatus.ACCEPTED);
    }

    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}/directores")
    public ResponseEntity<Resources<ProfesorResource>> getDirectores(@PathVariable long pfcId) {
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfcService.findByDirector(pfcId)));
    	profesoresResources.add(linkTo(methodOn(ProfesorControllerRest.class).getProfesores()).withSelfRel());
    	
        return new ResponseEntity<> (profesoresResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.PUT, value = "/{pfcId}/directores")
    public ResponseEntity<Resources<ProfesorResource>> addDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores) {
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfcService.addDirectors(pfcId, directores)));
    	profesoresResources.add(linkTo(methodOn(ProfesorControllerRest.class).getProfesores()).withSelfRel());
    	
        return new ResponseEntity<> (profesoresResources, HttpStatus.CREATED);
    }

    @RequestMapping (method = RequestMethod.DELETE, value = "/{pfcId}/directores")
    public ResponseEntity<Resources<ProfesorResource>> deleteDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores) {
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfcService.deleteDirectors(pfcId, directores)));
    	profesoresResources.add(linkTo(methodOn(ProfesorControllerRest.class).getProfesores()).withSelfRel());
    	
        return new ResponseEntity<> (profesoresResources, HttpStatus.ACCEPTED);
    }
}
