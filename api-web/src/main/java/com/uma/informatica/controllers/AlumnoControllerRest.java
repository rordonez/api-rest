package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.services.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaordonez on 16/02/14.
 */
@Controller
public class AlumnoControllerRest implements AlumnoController {

    private AlumnoService alumnoService;

    @Inject
    public AlumnoControllerRest(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }


    public List<Alumno> getAlumnos() {
        List<Alumno> alumnoList = new ArrayList<>();
        alumnoList.addAll(alumnoService.getAll());
        return alumnoList;
    }


    public Alumno getAlumno(@PathVariable Long alumnoId) {
        return alumnoService.findById(alumnoId);
    }


    public Alumno createAlumno(@RequestBody Alumno alumno) {
        return alumnoService.createAlumno(alumno.getDni(), alumno.getNombre(), alumno.getApellidos(), alumno.getTitulacion().name(), alumno.getDomicilio(), alumno.getLocalidad(), alumno.getPais(), alumno.getCodigoPostal(), alumno.getTelefono(), alumno.getEmail(), alumno.getFechaNacimiento());
    }


    public Alumno removeAlumno(@PathVariable Long alumnoId) {
        return alumnoService.deleteAlumno(alumnoId);
    }



    //FIXME Eliminar esto e incluir clases Request y Response
    /**
     * This is superior to using an {@link java.util.ArrayList} of {@link com.uma.informatica.persistence.models.Customer} because it bakes
     * in the generic type information which would've otherwise been lost and helps
     * Jackson in the conversion at runtime.
     */
//    static class AlumnoList extends ArrayList<Alumno> {
//
//        private static final long serialVersionUID = 1L;
//
//    }
}
