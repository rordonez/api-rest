package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Pfc;
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
    public Profesor getProfesor(@PathVariable long profesorId) {

        return this.profesorService.findById(profesorId);
    }

    @Override
    public Profesor createProfesor(@RequestBody Profesor profesor) {
        return this.profesorService.createProfesor(profesor.getDni(), profesor.getNombre(), profesor.getApellidos(), profesor.getTitulacion(), profesor.getTelefono(), profesor.getEmail());
    }

    @Override
    public Profesor removeProfesor(@PathVariable long profesorId) {
        return this.profesorService.deleteProfesor(profesorId);
    }

    @Override
    public List<Profesor> searchProfesores(@RequestParam String nombre, @RequestParam String apellidos, @RequestParam String email) {
        List<Profesor> profesorList = new ArrayList<>();
        if (nombre != null && apellidos != null) {
            profesorList.addAll(profesorService.findByNombreYApellidos(nombre, apellidos));
        }
        else {
            if (email != null) {
                profesorList.add(profesorService.findByEmail(email));
            }
        }
        return profesorList;
    }

    @Override
    public Profesor updateEmpresa(@PathVariable long profesorId, @RequestParam String empresa) {
        return profesorService.updateEmpresa(profesorId, empresa);
    }

    @Override
    public Profesor addEmpresa(@PathVariable long profesorId, @RequestParam String empresa) {
        return profesorService.addEmpresa(profesorId, empresa);
    }

    @Override
    public Profesor updateTelefono(@PathVariable long profesorId, @RequestParam String telefono) {
        return profesorService.updateTelefono(profesorId, telefono);
    }

    @Override
    public Profesor addTelefono(@PathVariable long profesorId, @RequestParam String telefono) {
        return profesorService.addTelefono(profesorId, telefono);
    }

    @Override
    public Profesor updateEmail(@PathVariable long profesorId, @RequestParam String email) {
        return profesorService.updateEmail(profesorId, email);
    }

    @Override
    public Profesor addEmail(@PathVariable long profesorId, @RequestParam String email) {
        return profesorService.addEmail(profesorId, email);
    }

    @Override
    public List<Pfc> getPfcs(@PathVariable long profesorId) {
        return profesorService.getPfcs(profesorId);
    }
}
