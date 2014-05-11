package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Profesor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by rafaordonez on 05/03/14.
 */
@RequestMapping(value = "/profesores", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ProfesorController {

    @RequestMapping (method = RequestMethod.GET)
    @ResponseBody
    List<Profesor> getProfesores();


    @RequestMapping (method = RequestMethod.GET, value = "/{profesorId}")
    @ResponseBody
    Profesor getProfesor(@PathVariable Long ProfesorId);


    @RequestMapping (method = RequestMethod.POST)
    @ResponseBody
    Profesor createProfesor(@RequestBody Profesor profesor);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{profesorId}")
    @ResponseBody
    Profesor removeProfesor(@PathVariable Long profesorId);

}