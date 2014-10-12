package com.uma.informatica.controllers;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by rafaordonez on 05/03/14.
 */
@Controller
@RequestMapping(value = "/pfcs", produces = MediaType.APPLICATION_JSON_VALUE)
public class PfcControllerDoc implements PfcController {

    private List<Pfc> pfcs = new ArrayList<>();
    private List<Profesor> profesores = new ArrayList<>();

    public PfcControllerDoc() {
        profesores.add(new Profesor("23478938L", "Pepe", "Romero Rodríquez", TitulacionEnum.GESTION, "678789434", "example@example.com" ));
        profesores.add(new Profesor("34689324O", "Rafa", "Puente Ramos", TitulacionEnum.SISTEMAS, "657478345", "example2@exmple.com"));
        profesores.add(new Profesor("74567890I", "Fernado", "Camino Rojas", TitulacionEnum.GESTION, "68903432", "example3@example.com"));

        Pfc pfc = new Pfc("Proyecto mock 1", "Departamento Ejemplo 1", EstadoPfc.EMPEZADO, new ArrayList<Profesor>(Arrays.asList(profesores.get(0), profesores.get(1))));
        pfc.setId(1L);
        pfcs.add(pfc);
        pfc = new Pfc("Proyecto mock 2", "Departamento Ejemplo 2", EstadoPfc.NO_EMPEZADO, new ArrayList<Profesor>(Arrays.asList(profesores.get(0))));
        pfc.setId(2L);
        pfcs.add(pfc);
        pfc = new Pfc("Proyecto mock 3", "Departamento Ejemplo 3", EstadoPfc.EMPEZADO, new ArrayList<Profesor>(Arrays.asList(profesores.get(2))));
        pfc.setId(3L);
        pfcs.add(pfc);

    }

    @Override
    @ApiOperation(value = "Devuelve la lista de todos los Pfc's disponibles")
    @RequestMapping(method = RequestMethod.GET)
    public List<Pfc> getPfcs() {
        return pfcs;
    }

    @Override
    @ApiOperation(value = "Devuelve la información de un Pfc dado su identificador")
    @RequestMapping(method = RequestMethod.GET, value = "/{pfcId}")
    public Pfc getPfc(@PathVariable long pfcId) {
        return findPfcById(pfcId);
    }



    @Override
    @ApiOperation(value = "Crea un nuevo Pfc dada toda la información necesaria para su creación")
    @RequestMapping(method = RequestMethod.POST)
    public Pfc createPfc(@RequestBody Pfc pfc) {
        pfc.setId(new Long(pfcs.size()));
        pfcs.add(pfc);
        return pfc;
    }

    @Override
    public List<Pfc> searchPfcs(@RequestParam final String departamento, @RequestParam final String nombre, @RequestParam final EstadoPfc estado) {

        Predicate<Pfc> searchPfc = new Predicate<Pfc>() {
            @Override
            public boolean apply(Pfc pfc) {
                return pfc.getDepartamento().equals(departamento) &&
                        pfc.getEstado().equals(estado) &&
                        pfc.getNombre().equals(nombre);
            }
        };

        List<Pfc> founded = FluentIterable.from(pfcs)
                .filter(searchPfc)
                .toList();

        return founded;
    }


    @Override
    @ApiOperation(value = "Borra un Pfc dado su identificador")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{pfcId}")
    public Pfc removePfc(@PathVariable long pfcId) {
        Pfc pfc = findPfcById(pfcId);
        pfcs.remove(pfc);
        return pfc;
    }

    @Override
    public Pfc updateNombre(@PathVariable long pfcId, @RequestParam String nombre) {
        Pfc pfc = findPfcById(pfcId);
        pfc.setNombre(nombre);
        return pfc;
    }

    @Override
    public Pfc updateEstado(@PathVariable long pfcId, @RequestParam EstadoPfc estado) {
        Pfc pfc = findPfcById(pfcId);
        pfc.setEstado(estado);
        return pfc;
    }

    @Override
    public Profesor getDirectorAcademico(@PathVariable long pfcId) {
        Pfc pfc = findPfcById(pfcId);
        return pfc.getDirectorAcademico();
    }


    private Pfc findPfcById(long pfcId) {
        Pfc pfcEncontrado = null;
        for (int i = 0; pfcEncontrado == null && i < pfcs.size(); i++) {
            Pfc pfc = pfcs.get(i);
            if(pfc.getId().equals(pfcId)) {
                pfcEncontrado = pfc;
            }
            i++;
        }
        return pfcEncontrado;
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

    @Override
    public Pfc updateFechaFin(@PathVariable long pfcId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        Pfc pfc = findPfcById(pfcId);
        pfc.setFechaFin(fechaFin);
        return pfc;
    }

    @Override
    public Profesor changeDirectorAcademico(@PathVariable long pfcId, @RequestBody long profesor) {
        Pfc pfc = findPfcById(pfcId);
        Profesor p = findProfesorById(profesor);
        pfc.setDirectorAcademico(p);
        return p;
    }

    @Override
    public List<Profesor> getDirectores(@PathVariable long pfcId) {
        Pfc pfc = findPfcById(pfcId);
        return pfc.getDirectores();
    }

    @Override
    public List<Profesor> addDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores) {
        Pfc pfc = findPfcById(pfcId);

        List<Profesor> profesorsList = new ArrayList<>();
        for(Profesor p : profesores) {
            for(Long id : directores) {
                if(p.getId().longValue() == id)
                    profesorsList.add(p);
            }
        }
        pfc.setDirectores(profesorsList);
        return pfc.getDirectores();
    }

    @Override
    public List<Profesor> deleteDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores) {
        Pfc pfc = findPfcById(pfcId);

        List<Profesor> profesorsList = new ArrayList<>();
        for(Profesor p : profesores) {
            for(Long id : directores) {
                if(p.getId().longValue() == id)
                    profesorsList.add(p);
            }
        }
        pfc.getDirectores().removeAll(profesorsList);
        return pfc.getDirectores();
    }
}
