package com.uma.informatica.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uma.informatica.controllers.resources.PfcResourceAssembler;
import com.uma.informatica.controllers.resources.ProfesorResourceAssembler;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.persistence.services.PfcService;
import com.uma.informatica.resources.PfcResource;
import com.uma.informatica.resources.ProfesorResource;

/**
 * Created by rafaordonez on 26/02/14.
 */

@ExposesResourceFor(Pfc.class)
@RestController
@RequestMapping(value = "/pfcs", produces = MediaType.APPLICATION_JSON_VALUE)
public class PfcControllerRest implements PfcController {

    private PfcService pfcService;

    private final PfcResourceAssembler pfcResourceAssembler;
    private final ProfesorResourceAssembler profesorResourceAssembler;
    
    @Inject
    PfcControllerRest(PfcService pfcService, PfcResourceAssembler pfcResourceAssembler, ProfesorResourceAssembler profesorResourceAssembler) {
        this.pfcService = pfcService;
        this.pfcResourceAssembler = pfcResourceAssembler;
        this.profesorResourceAssembler = profesorResourceAssembler;
    }

    @Override
    public ResponseEntity<Resources<PfcResource>> getPfcs() {
    	Resources<PfcResource> pfcsResources = new Resources<PfcResource>(pfcResourceAssembler.toResources(pfcService.getAll()));
    	pfcsResources.add(linkTo(methodOn(PfcControllerRest.class).getPfcs()).withSelfRel());
    	
        return new ResponseEntity<> (pfcsResources, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Resources<PfcResource>> searchPfcs(@RequestParam(required = false) String departamento, @RequestParam(required = false) String nombre, @RequestParam(required = false) EstadoPfc estado) {
    	Resources<PfcResource> pfcsResources = new Resources<>(pfcResourceAssembler.toResources(pfcService.search(departamento, nombre, estado != null ? estado.name() : null)));
        return new ResponseEntity<> (pfcsResources, HttpStatus.OK);
    }

    
    @Override
    public ResponseEntity<PfcResource> getPfc(@PathVariable long pfcId) {
    	return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.findById(pfcId)), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<PfcResource> createPfc(@RequestBody Pfc pfc) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.createPfc(pfc.getNombre(), pfc.getDepartamento(), pfc.getEstado(), new ArrayList<Long>())), HttpStatus.CREATED);
    }



    @Override
    public ResponseEntity<PfcResource> removePfc(@PathVariable long pfcId) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.deletePfc(pfcId)), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<PfcResource> updateNombre(@PathVariable long pfcId, @RequestParam String nombre) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.updateNombre(pfcId, nombre)),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PfcResource> updateEstado(@PathVariable long pfcId, @RequestParam EstadoPfc estado) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.updateEstado(pfcId, estado)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProfesorResource> getDirectorAcademico(@PathVariable long pfcId) {
        return new ResponseEntity<>(profesorResourceAssembler.toResource(pfcService.findByDirectorAcademico(pfcId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PfcResource> updateFechaFin(@PathVariable long pfcId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.updateFechaFin(pfcId, fechaFin)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProfesorResource> changeDirectorAcademico(@PathVariable long pfcId, @RequestParam long profesor) {
        return new ResponseEntity<>(profesorResourceAssembler.toResource(pfcService.changeDirectorAcademico(pfcId, profesor)), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Resources<ProfesorResource>> getDirectores(@PathVariable long pfcId) {
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfcService.findByDirector(pfcId)));
    	profesoresResources.add(linkTo(methodOn(ProfesorControllerRest.class).getProfesores()).withSelfRel());
    	
        return new ResponseEntity<> (profesoresResources, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resources<ProfesorResource>> addDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores) {
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfcService.addDirectors(pfcId, directores)));
    	profesoresResources.add(linkTo(methodOn(ProfesorControllerRest.class).getProfesores()).withSelfRel());
    	
        return new ResponseEntity<> (profesoresResources, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Resources<ProfesorResource>> deleteDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores) {
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfcService.deleteDirectors(pfcId, directores)));
    	profesoresResources.add(linkTo(methodOn(ProfesorControllerRest.class).getProfesores()).withSelfRel());
    	
        return new ResponseEntity<> (profesoresResources, HttpStatus.ACCEPTED);
    }
}
