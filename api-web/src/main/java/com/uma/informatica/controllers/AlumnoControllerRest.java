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
    public List<Alumno> getAlumnos() {
        List<Alumno> alumnoList = new ArrayList<>();
        alumnoList.addAll(alumnoService.getAll());
        return alumnoList;
    }


    @Override
    public ResponseEntity<Resource<Alumno>> getAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.findById(alumnoId)), HttpStatus.OK);
    }


    @Override
    public Alumno createAlumno(@Valid @RequestBody Alumno alumno) {
        return alumnoService.createAlumno(alumno.getDni(), alumno.getNombre(), alumno.getApellidos(), alumno.getTitulacion().name(), alumno.getDomicilio(), alumno.getLocalidad(), alumno.getPais(), alumno.getCodigoPostal(), alumno.getTelefono(), alumno.getEmail(), alumno.getFechaNacimiento());
    }

    @Override
    public Alumno removeAlumno(@PathVariable long alumnoId) {
        return alumnoService.deleteAlumno(alumnoId);
    }


    @Override
    public List<Alumno> searchAlumnos(@Valid @RequestBody SearchAlumnoRequestBody search) {
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
        return alumnoList;
    }


    @Override
    public Alumno updateDireccion(@PathVariable long alumnoId, @Valid @RequestBody DireccionRequestBody direccion) {
        return alumnoService.updateDireccion(alumnoId, direccion.getDomicilio(), direccion.getLocalidad(), direccion.getPais(), direccion.getCodigoPostal());
    }

    @Override
    public Alumno updateEmail(@PathVariable long alumnoId, @NotNull @RequestParam String email) {
        return alumnoService.updateEmail(alumnoId, email);
    }

    @Override
    public Alumno updateTelefono(@PathVariable long alumnoId, @NotNull @RequestParam String telefono) {
        return alumnoService.updateTelefono(alumnoId, telefono);
    }

    @Override
    public Pfc getPfc(@PathVariable long alumnoId) {
        return pfcService.getPfcFromAlumno(alumnoId);
    }

    @Override
    public Pfc addPfc(@PathVariable long alumnoId, @Valid @RequestBody Pfc pfc) {
        return pfcService.addPfcToAlumno(alumnoId, pfc.getNombre(), pfc.getDepartamento());
    }

    @Override
    public Pfc deletePfc(@PathVariable long alumnoId) {
        return pfcService.deletePfcFromAlumno(alumnoId);
    }
}
