package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Pfc;
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
    Profesor getProfesor(@PathVariable long ProfesorId);


    @RequestMapping (method = RequestMethod.PUT)
    @ResponseBody
    Profesor createProfesor(@RequestBody Profesor profesor);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{profesorId}")
    @ResponseBody
    Profesor removeProfesor(@PathVariable long profesorId);

    @RequestMapping (method = RequestMethod.GET, value = "/search")
    @ResponseBody
    List<Profesor> searchProfesores(@RequestParam final String nombre, @RequestParam String apellidos, @RequestParam String email);


    @RequestMapping (method = RequestMethod.POST, value = "/{profesorId}/empresa")
    @ResponseBody
    Profesor updateEmpresa(@PathVariable long profesorId, @RequestParam String empresa);


    @RequestMapping (method = RequestMethod.PUT, value = "/{profesorId}/empresa")
    @ResponseBody
    Profesor addEmpresa(@PathVariable long profesorId, @RequestParam String empresa);


    @RequestMapping (method = RequestMethod.POST, value = "/{profesorId}/telefono")
    @ResponseBody
    Profesor updateTelefono(@PathVariable long profesorId, @RequestParam String telefono);


    @RequestMapping (method = RequestMethod.PUT, value = "/{profesorId}/telefono")
    @ResponseBody
    Profesor addTelefono(@PathVariable long profesorId, @RequestParam String telefono);


    @RequestMapping (method = RequestMethod.POST, value = "/{profesorId}/email")
    @ResponseBody
    Profesor updateEmail(@PathVariable long profesorId, @RequestParam String email);


    @RequestMapping (method = RequestMethod.PUT, value = "/{profesorId}/email")
    @ResponseBody
    Profesor addEmail(@PathVariable long profesorId, @RequestParam String email);

    @RequestMapping (method = RequestMethod.GET, value = "/{profesorId}/pfcs")
    @ResponseBody
    List<Pfc> getPfcs(@PathVariable long profesorId);

}