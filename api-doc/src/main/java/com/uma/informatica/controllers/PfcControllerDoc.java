package com.uma.informatica.controllers;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.uma.informatica.controllers.resources.PfcResourceAssembler;
import com.uma.informatica.controllers.resources.ProfesorResourceAssembler;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import com.uma.informatica.resources.PfcResource;
import com.uma.informatica.resources.ProfesorResource;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by rafaordonez on 05/03/14.
 */
@RestController
@ExposesResourceFor(Pfc.class)
@RequestMapping(value = "/pfcs", produces = MediaType.APPLICATION_JSON_VALUE)
public class PfcControllerDoc implements PfcController {

    private List<Pfc> pfcs = new ArrayList<>();
    private List<Profesor> profesores = new ArrayList<>();
    
    private final PfcResourceAssembler pfcResourceAssembler;
    private final ProfesorResourceAssembler profesorResourceAssembler;


    @Inject
    public PfcControllerDoc(PfcResourceAssembler pfcResourceAssembler, ProfesorResourceAssembler profesorResourceAssembler) {
        profesores.add(new Profesor("23478938L", "Pepe", "Romero Rodríquez", TitulacionEnum.GESTION, "678789434", "example@example.com" ));
        profesores.add(new Profesor("34689324O", "Rafa", "Puente Ramos", TitulacionEnum.SISTEMAS, "657478345", "example2@exmple.com"));
        profesores.add(new Profesor("74567890I", "Fernado", "Camino Rojas", TitulacionEnum.GESTION, "68903432", "example3@example.com"));

        Pfc pfc = new Pfc("Proyecto mock 1", "Departamento Ejemplo 1", EstadoPfc.EMPEZADO, new ArrayList<Profesor>(Arrays.asList(profesores.get(0), profesores.get(1))));
        pfc.setId(1L);
        pfcs.add(pfc);
        pfc = new Pfc("Proyecto mock 2", "Departamento Ejemplo 2", EstadoPfc.NO_EMPEZADO, new ArrayList<Profesor>(Arrays.asList(profesores.get(0))));
        pfc.setId(2L);
        pfcs.add(pfc);
        pfc = new Pfc("Proyecto mock 3", "Departamento Ejemplo 3", EstadoPfc.EMPEZADO, new ArrayList<Profesor>(Arrays.asList(profesores.get(2))));
        pfc.setId(3L);
        pfcs.add(pfc);

        this.pfcResourceAssembler = pfcResourceAssembler;
        this.profesorResourceAssembler = profesorResourceAssembler;
    }

    @Override
    @ApiOperation(value = "Devuelve la lista de todos los Pfc's disponibles")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resources<PfcResource>> getPfcs() {
    	Resources<PfcResource> pfcsResources = new Resources<PfcResource>(pfcResourceAssembler.toResources(pfcs));
    	pfcsResources.add(linkTo(methodOn(PfcController.class).getPfcs()).withSelfRel());
    	
        return new ResponseEntity<> (pfcsResources, HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Devuelve la información de un Pfc dado su identificador")
    @RequestMapping(method = RequestMethod.GET, value = "/{pfcId}")
    public ResponseEntity<PfcResource> getPfc(@PathVariable long pfcId) {
    	return new ResponseEntity<>(pfcResourceAssembler.toResource(findPfcById(pfcId)), HttpStatus.OK);
    }



    @Override
    @ApiOperation(value = "Crea un nuevo Pfc dada toda la información necesaria para su creación")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PfcResource> createPfc(@RequestBody Pfc pfc) {
        pfc.setId(new Long(pfcs.size()));
        pfcs.add(pfc);
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfc), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Resources<PfcResource>> searchPfcs(@RequestParam final String departamento, @RequestParam final String nombre, @RequestParam final EstadoPfc estado) {

        Predicate<Pfc> searchPfc = new Predicate<Pfc>() {
            @Override
            public boolean apply(Pfc pfc) {
                return pfc.getDepartamento().equals(departamento) &&
                        pfc.getEstado().equals(estado) &&
                        pfc.getNombre().equals(nombre);
            }
        };

        List<Pfc> founded = FluentIterable.from(pfcs)
                .filter(searchPfc)
                .toList();

    	Resources<PfcResource> pfcsResources = new Resources<PfcResource>(pfcResourceAssembler.toResources(founded));
    	pfcsResources.add(linkTo(methodOn(PfcController.class).getPfcs()).withSelfRel());
    	
        return new ResponseEntity<> (pfcsResources, HttpStatus.OK);
    }


    @Override
    @ApiOperation(value = "Borra un Pfc dado su identificador")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{pfcId}")
    public ResponseEntity<PfcResource> removePfc(@PathVariable long pfcId) {
        Pfc pfc = findPfcById(pfcId);
        pfcs.remove(pfc);
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfc), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<PfcResource> updateNombre(@PathVariable long pfcId, @RequestParam String nombre) {
        Pfc pfc = findPfcById(pfcId);
        pfc.setNombre(nombre);
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PfcResource> updateEstado(@PathVariable long pfcId, @RequestParam EstadoPfc estado) {
        Pfc pfc = findPfcById(pfcId);
        pfc.setEstado(estado);
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProfesorResource> getDirectorAcademico(@PathVariable long pfcId) {
        Pfc pfc = findPfcById(pfcId);
        return new ResponseEntity<>(profesorResourceAssembler.toResource(pfc.getDirectorAcademico()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PfcResource> updateFechaFin(@PathVariable long pfcId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        Pfc pfc = findPfcById(pfcId);
        pfc.setFechaFin(fechaFin);
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProfesorResource> changeDirectorAcademico(@PathVariable long pfcId, @RequestBody long profesor) {
        Pfc pfc = findPfcById(pfcId);
        Profesor p = findProfesorById(profesor);
        pfc.setDirectorAcademico(p);
        return new ResponseEntity<>(profesorResourceAssembler.toResource(p), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resources<ProfesorResource>> getDirectores(@PathVariable long pfcId) {
        Pfc pfc = findPfcById(pfcId);
        
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfc.getDirectores()));
    	profesoresResources.add(linkTo(methodOn(PfcController.class).getPfcs()).withSelfRel());
    	
        return new ResponseEntity<> (profesoresResources, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Resources<ProfesorResource>> addDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores) {
        Pfc pfc = findPfcById(pfcId);

        List<Profesor> profesorsList = new ArrayList<>();
        for(Profesor p : profesores) {
            for(Long id : directores) {
                if(p.getId().longValue() == id)
                    profesorsList.add(p);
            }
        }
        pfc.setDirectores(profesorsList);
        
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfc.getDirectores()));
    	profesoresResources.add(linkTo(methodOn(PfcController.class).getPfcs()).withSelfRel());
    	
        return new ResponseEntity<> (profesoresResources, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Resources<ProfesorResource>> deleteDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores) {
        Pfc pfc = findPfcById(pfcId);

        List<Profesor> profesorsList = new ArrayList<>();
        for(Profesor p : profesores) {
            for(Long id : directores) {
                if(p.getId().longValue() == id)
                    profesorsList.add(p);
            }
        }
        pfc.getDirectores().removeAll(profesorsList);
        
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfc.getDirectores()));
    	profesoresResources.add(linkTo(methodOn(PfcController.class).getPfcs()).withSelfRel());
    	
        return new ResponseEntity<> (profesoresResources, HttpStatus.ACCEPTED);
    }
    
    private Pfc findPfcById(long pfcId) {
        Pfc pfcEncontrado = null;
        for (int i = 0; pfcEncontrado == null && i < pfcs.size(); i++) {
            Pfc pfc = pfcs.get(i);
            if(pfc.getId().equals(pfcId)) {
                pfcEncontrado = pfc;
            }
            i++;
        }
        return pfcEncontrado;
    }

    private Profesor findProfesorById(long profesorId) {
        Profesor profesorEncontrado = null;
        for (int i = 0; profesorEncontrado== null && i < profesores.size(); i++) {
            Profesor profesor = profesores.get(i);
            if(profesor.getId().equals(profesorId)) {
                profesorEncontrado = profesor;
            }
            i++;
        }
        return profesorEncontrado;
    }
}
