package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Alumno;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by rafaordonez on 02/03/14.
 */
@RequestMapping(value = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AlumnoController {


    @RequestMapping (method = RequestMethod.GET)
    @ResponseBody
    List<Alumno> getAlumnos();


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}")
    @ResponseBody
    Alumno getAlumno(@PathVariable Long alumnoId);


    @RequestMapping (method = RequestMethod.POST)
    @ResponseBody
    Alumno createAlumno(@RequestBody Alumno alumno);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}")
    @ResponseBody
    Alumno removeAlumno(@PathVariable Long alumnoId);

}