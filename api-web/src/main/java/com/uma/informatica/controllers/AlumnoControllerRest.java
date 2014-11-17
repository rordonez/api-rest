package com.uma.informatica.controllers;

import com.uma.informatica.controllers.beans.DireccionRequestBody;
import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.services.AlumnoService;
import com.uma.informatica.persistence.services.PfcService;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaordonez on 16/02/14.
 */
@ExposesResourceFor(Alumno.class)
@RestController
public class AlumnoControllerRest implements AlumnoController {

    private AlumnoService alumnoService;
    private PfcService pfcService;

    private final ResourceAssembler<Alumno, Resource<Alumno>> alumnoResourceAssembler;
    private final ResourceAssembler<Pfc, Resource<Pfc>> pfcResourceAssembler;

    @Inject
    public AlumnoControllerRest(AlumnoService alumnoService, PfcService pfcService, ResourceAssembler<Alumno, Resource<Alumno>> alumnoResourceAssembler, ResourceAssembler<Pfc, Resource<Pfc>> pfcResourceAssembler) {
        this.alumnoService = alumnoService;
        this.pfcService = pfcService;
        this.alumnoResourceAssembler = alumnoResourceAssembler;
        this.pfcResourceAssembler = pfcResourceAssembler;
    }


    @Override
    public ResponseEntity<Resources<Resource<Alumno>>> getAlumnos() {
        return new ResponseEntity<> (Resources.wrap(alumnoService.getAll()), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Resource<Alumno>> getAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.findById(alumnoId)), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Resource<Alumno>> createAlumno(@Valid @RequestBody Alumno alumno) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.createAlumno(alumno.getDni(), alumno.getNombre(), alumno.getApellidos(), alumno.getTitulacion().name(), alumno.getDomicilio(), alumno.getLocalidad(), alumno.getPais(), alumno.getCodigoPostal(), alumno.getTelefono(), alumno.getEmail(), alumno.getFechaNacimiento())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Resource<Alumno>> removeAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.deleteAlumno(alumnoId)), HttpStatus.ACCEPTED);
    }


    @Override
    public ResponseEntity<Resources<Resource<Alumno>>> searchAlumnos(@Valid @RequestBody SearchAlumnoRequestBody search) {
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
        return new ResponseEntity<> (Resources.wrap(alumnoList), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Resource<Alumno>> updateDireccion(@PathVariable long alumnoId, @Valid @RequestBody DireccionRequestBody direccion) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.updateDireccion(alumnoId, direccion.getDomicilio(), direccion.getLocalidad(), direccion.getPais(), direccion.getCodigoPostal())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource<Alumno>> updateEmail(@PathVariable long alumnoId, @NotNull @RequestParam String email) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.updateEmail(alumnoId, email)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource<Alumno>> updateTelefono(@PathVariable long alumnoId, @NotNull @RequestParam String telefono) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.updateTelefono(alumnoId, telefono)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource<Pfc>> getPfc(@PathVariable long alumnoId) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.getPfcFromAlumno(alumnoId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource<Pfc>> addPfc(@PathVariable long alumnoId, @Valid @RequestBody Pfc pfc) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.addPfcToAlumno(alumnoId, pfc.getNombre(), pfc.getDepartamento())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Resource<Pfc>> deletePfc(@PathVariable long alumnoId) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.deletePfcFromAlumno(alumnoId)), HttpStatus.ACCEPTED);
    }
}
