package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rafaordonez on 02/03/14.
 */
@Controller
@RequestMapping(value = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlumnoControllerDoc implements AlumnoController {

    private List<Alumno> alumnos = new ArrayList<>();

    public AlumnoControllerDoc() {
        alumnos.add(new Alumno("012345678A", "Pedro", "Moratilla Barrios", TitulacionEnum.SUPERIOR, "calle Pez", "Madrid", "España", "28080", "657465746", "ex@dm.com", new Date()));
        alumnos.add(new Alumno("367473822S", "Luis", "Santos Barrios", TitulacionEnum.GESTION, "calle Ruis, 23", "Madrid", "España", "28080", "657342787", "ex2@dm.com", new Date()));
        alumnos.add(new Alumno("367583094A", "Rafa", "Cruz Rios", TitulacionEnum.SISTEMAS, "calle Principal, 1", "Málaga", "España", "19080", "673265743", "ex3@dm.com", new Date()));
    }

    @Override
    @ApiOperation(value = "Devuelve la lista de alumnos")
    @RequestMapping(method = RequestMethod.GET)
    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    @Override
    @ApiOperation(value = "Devuelve un alumno dado su identificador")
    @RequestMapping(value = "/{alumnoId}", method = RequestMethod.GET)
    public Alumno getAlumno(@PathVariable Long alumnoId) {
        return alumnos.get(alumnoId.intValue());
    }

    @Override
    @ApiOperation(value = "Crea un alumno con la información del alumno")
    @RequestMapping(method = RequestMethod.POST)
    public Alumno createAlumno(@RequestBody Alumno alumno) {
        alumnos.add(alumno);
        return alumno;
    }

    @Override
    @ApiOperation(value = "Borrar el alumno dado su identificador")
    @RequestMapping(value = "/{alumnoId}",method = RequestMethod.DELETE)
    public Alumno removeAlumno(@PathVariable Long alumnoId) {
        return alumnos.remove(alumnoId.intValue());
    }
}
