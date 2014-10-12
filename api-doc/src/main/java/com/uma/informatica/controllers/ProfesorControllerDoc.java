package com.uma.informatica.controllers;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.uma.informatica.persistence.models.Pfc;
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
        profesores.add(new Profesor("12345678A", "Nombre 1", "Ap Ap", TitulacionEnum.SISTEMAS, "666555777", "example@com.com"));
        profesores.add(new Profesor("12345678A", "Nombre 2", "Ap Ap", TitulacionEnum.SISTEMAS, "666555777", "example@com.com"));
        profesores.add(new Profesor("12345678A", "Nombre 3", "Ap Ap", TitulacionEnum.SISTEMAS, "666555777", "example@com.com"));
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Profesor> getProfesores() {
        return profesores;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{profesorId}")
    @ResponseBody
    public Profesor getProfesor(@PathVariable long profesorId) {
        return profesores.get((int)profesorId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Profesor createProfesor(@RequestBody Profesor profesor) {
        profesores.add(profesor);
        return profesor;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{profesorId}")
    @ResponseBody
    public Profesor removeProfesor(@PathVariable long profesorId) {
        return profesores.remove((int)profesorId);
    }

    @RequestMapping (method = RequestMethod.GET)
    @ResponseBody
    public List<Profesor> searchProfesores(@RequestParam final String nombre, @RequestParam final String apellidos, @RequestParam final String email) {
        Predicate<Profesor> searchProfesor = new Predicate<Profesor>() {
            @Override
            public boolean apply(Profesor profesor) {
                return profesor.getNombre().equals(nombre) &&
                        profesor.getApellidos().equals(apellidos) &&
                        profesor.getEmail().equals(email);
            }
        };

        return FluentIterable.from(profesores)
                .filter(searchProfesor)
                .toList();
    }


    @RequestMapping (method = RequestMethod.POST, value = "/{profesorId}/empresa")
    @ResponseBody
    public Profesor updateEmpresa(@PathVariable long profesorId, @RequestParam String empresa) {
        Profesor profesor = findProfesorById(profesorId);
        profesor.setEmpresa(empresa);
        return profesor;
    }


    @RequestMapping (method = RequestMethod.PUT, value = "/{profesorId}/empresa")
    @ResponseBody
    public Profesor addEmpresa(@PathVariable long profesorId, @RequestParam String empresa) {
        Profesor profesor = findProfesorById(profesorId);
        profesor.setEmpresa(empresa);
        return profesor;
    }


    @RequestMapping (method = RequestMethod.POST, value = "/{profesorId}/telefono")
    @ResponseBody
    public Profesor updateTelefono(@PathVariable long profesorId, @RequestParam String telefono) {
        Profesor profesor = findProfesorById(profesorId);
        profesor.setTelefono(telefono);
        return profesor;
    }


    @RequestMapping (method = RequestMethod.PUT, value = "/{profesorId}/telefono")
    @ResponseBody
    public Profesor addTelefono(@PathVariable long profesorId, @RequestParam String telefono) {
        Profesor profesor = findProfesorById(profesorId);
        profesor.setTelefono(telefono);
        return profesor;
    }


    @RequestMapping (method = RequestMethod.POST, value = "/{profesorId}/email")
    @ResponseBody
    public Profesor updateEmail(@PathVariable long profesorId, @RequestParam String email) {
        Profesor profesor = findProfesorById(profesorId);
        profesor.setEmail(email);
        return profesor;
    }


    @RequestMapping (method = RequestMethod.PUT, value = "/{profesorId}/email")
    @ResponseBody
    public Profesor addEmail(@PathVariable long profesorId, @RequestParam String email) {
        Profesor profesor = findProfesorById(profesorId);
        profesor.setEmail(email);
        return profesor;
    }

    @RequestMapping (method = RequestMethod.GET, value = "/{profesorId}/pfcs")
    @ResponseBody
    public List<Pfc> getPfcs(@PathVariable long profesorId) {
        Profesor profesor = findProfesorById(profesorId);
        return (List) profesor.getPfcs();
    }


    private Profesor findProfesorById(long profesorId) {
        Profesor profesorEncontrado = null;
        for (int i = 0; profesorEncontrado== null && i < profesores.size(); i++) {
            Profesor profesor = profesores.get(i);
            if(profesor.getId().equals(profesorId)) {
                profesorEncontrado = profesor;
            }
            i++;
        }
        return profesorEncontrado;
    }
}
