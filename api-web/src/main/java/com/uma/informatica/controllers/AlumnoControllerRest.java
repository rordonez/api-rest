package com.uma.informatica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.uma.informatica.controllers.beans.DireccionRequestBody;
import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.services.AlumnoService;
import com.uma.informatica.persistence.services.PfcService;

/**
 * Created by rafaordonez on 16/02/14.
 */
@Controller
public class AlumnoControllerRest implements AlumnoController {

    private AlumnoService alumnoService;
    private PfcService pfcService;

    @Inject
    public AlumnoControllerRest(AlumnoService alumnoService, PfcService pfcService) {
        this.alumnoService = alumnoService;
        this.pfcService = pfcService;
    }


    @Override
    public List<Alumno> getAlumnos() {
        List<Alumno> alumnoList = new ArrayList<>();
        alumnoList.addAll(alumnoService.getAll());
        return alumnoList;
    }


    @Override
    public Alumno getAlumno(@PathVariable long alumnoId) {
        return alumnoService.findById(alumnoId);
    }


    @Override
    public Alumno createAlumno(@RequestBody Alumno alumno) {
        return alumnoService.createAlumno(alumno.getDni(), alumno.getNombre(), alumno.getApellidos(), alumno.getTitulacion().name(), alumno.getDomicilio(), alumno.getLocalidad(), alumno.getPais(), alumno.getCodigoPostal(), alumno.getTelefono(), alumno.getEmail(), alumno.getFechaNacimiento());
    }

    @Override
    public Alumno removeAlumno(@PathVariable long alumnoId) {
        return alumnoService.deleteAlumno(alumnoId);
    }


    @Override
    public List<Alumno> searchAlumnos(@NotNull @RequestBody SearchAlumnoRequestBody search) {
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
    public Alumno updateDireccion(@PathVariable long alumnoId, @RequestBody DireccionRequestBody direccion) {
        return alumnoService.updateDireccion(alumnoId, direccion.getDomicilio(), direccion.getLocalidad(), direccion.getPais(), direccion.getCodigoPostal());
    }

    @Override
    public Alumno updateEmail(@PathVariable long alumnoId, @RequestParam String email) {
        return alumnoService.updateEmail(alumnoId, email);
    }

    @Override
    public Alumno updateTelefono(@PathVariable long alumnoId, @RequestParam String telefono) {
        return alumnoService.updateTelefono(alumnoId, telefono);
    }

    @Override
    public Pfc getPfc(@PathVariable long alumnoId) {
        return pfcService.getPfcFromAlumno(alumnoId);
    }

    @Override
    public Pfc addPfc(@PathVariable long alumnoId, @RequestBody Pfc pfc) {
        return pfcService.addPfcToAlumno(alumnoId, pfc.getNombre(), pfc.getDepartamento());
    }

    @Override
    public Pfc deletePfc(@PathVariable long alumnoId) {
        return pfcService.deletePfcFromAlumno(alumnoId);
    }
}
