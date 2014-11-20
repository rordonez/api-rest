package com.uma.informatica.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AlumnoResource> getAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.findById(alumnoId)), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<AlumnoResource> createAlumno(@Valid @RequestBody Alumno alumno) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.createAlumno(alumno.getDni(), alumno.getNombre(), alumno.getApellidos(), alumno.getTitulacion().name(), alumno.getDomicilio(), alumno.getLocalidad(), alumno.getPais(), alumno.getCodigoPostal(), alumno.getTelefono(), alumno.getEmail(), alumno.getFechaNacimiento())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AlumnoResource> removeAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.deleteAlumno(alumnoId)), HttpStatus.ACCEPTED);
    }


    @Override
    public ResponseEntity<Resources<AlumnoResource>> searchAlumnos(@Valid @RequestBody SearchAlumnoRequestBody search) {
        List<Alumno> alumnoList = new ArrayList<>();
        if (search.getDni() != null) {
            alumnoList.add(alumnoService.findByDni(search.getDni()));
        }
        else {
            if (search.getApellidos() != null) {
                alumnoList.addAll(alumnoService.findByNombreYApellidos(search.getNombre(), search.getApellidos()));
            }
            else {
                alumnoList.addAll(alumnoService.findByNombre(search.getNombre()));
            }
        }
        Resources<AlumnoResource> alumnosResources = new Resources<AlumnoResource>(alumnoResourceAssembler.toResources(alumnoList));
    	alumnosResources.add(linkTo(methodOn(AlumnoControllerRest.class).searchAlumnos(search)).withSelfRel());
    	
        return new ResponseEntity<> (alumnosResources, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<AlumnoResource> updateDireccion(@PathVariable long alumnoId, @Valid @RequestBody DireccionRequestBody direccion) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.updateDireccion(alumnoId, direccion.getDomicilio(), direccion.getLocalidad(), direccion.getPais(), direccion.getCodigoPostal())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlumnoResource> updateEmail(@PathVariable long alumnoId, @NotNull @RequestParam String email) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.updateEmail(alumnoId, email)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlumnoResource> updateTelefono(@PathVariable long alumnoId, @NotNull @RequestParam String telefono) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.updateTelefono(alumnoId, telefono)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PfcResource> getPfc(@PathVariable long alumnoId) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.getPfcFromAlumno(alumnoId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PfcResource> addPfc(@PathVariable long alumnoId, @Valid @RequestBody Pfc pfc) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.addPfcToAlumno(alumnoId, pfc.getNombre(), pfc.getDepartamento())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PfcResource> deletePfc(@PathVariable long alumnoId) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.deletePfcFromAlumno(alumnoId)), HttpStatus.ACCEPTED);
    }
}
