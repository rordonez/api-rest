package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaordonez on 06/03/14.
 */
@Controller
@RequestMapping(value = "/profesores", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfesorControllerDoc implements ProfesorController {

    private List<Profesor> profesores = new ArrayList<>();

    public ProfesorControllerDoc() {
        profesores.add(new Profesor("12345678A", "Nombre 1", "Ap Ap", TitulacionEnum.SISTEMAS.name(), "666555777", "example@com.com"));
        profesores.add(new Profesor("12345678A", "Nombre 2", "Ap Ap", TitulacionEnum.SISTEMAS.name(), "666555777", "example@com.com"));
        profesores.add(new Profesor("12345678A", "Nombre 3", "Ap Ap", TitulacionEnum.SISTEMAS.name(), "666555777", "example@com.com"));
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Profesor> getProfesores() {
        return profesores;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{profesorId}")
    @ResponseBody
    public Profesor getProfesor(@PathVariable Long profesorId) {
        return profesores.get(profesorId.intValue());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Profesor createProfesor(@RequestBody Profesor profesor) {
        profesores.add(profesor);
        return profesor;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{profesorId}")
    @ResponseBody
    public Profesor removeProfesor(@PathVariable Long profesorId) {
        return profesores.remove(profesorId.intValue());
    }
}
