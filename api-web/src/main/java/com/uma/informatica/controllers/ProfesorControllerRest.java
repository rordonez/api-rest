package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.services.ProfesorService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaordonez on 02/03/14.
 */
@Controller
@RequestMapping(value = "/profesores", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfesorControllerRest implements ProfesorController {

    private ProfesorService profesorService;

    @Inject
    public ProfesorControllerRest(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    @Override
    public List<Profesor> getProfesores() {
        List<Profesor> profesors = new ArrayList<>();
        profesors.addAll(this.profesorService.getAll());
        return profesors;
    }

    @Override
    public Profesor getProfesor(@PathVariable Long profesorId) {
        return this.profesorService.findById(profesorId);
    }

    @Override
    public Profesor createProfesor(@RequestBody Profesor profesor) {
        return this.profesorService.createProfesor(profesor.getDni(), profesor.getNombre(), profesor.getApellidos(), profesor.getTitulacion(), profesor.getTelefono(), profesor.getEmail());
    }

    @Override
    public Profesor removeProfesor(@PathVariable Long profesorId) {
        return this.profesorService.deleteProfesor(profesorId);
    }
}
