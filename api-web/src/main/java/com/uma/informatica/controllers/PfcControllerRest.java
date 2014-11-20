package com.uma.informatica.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.persistence.services.PfcService;

/**
 * Created by rafaordonez on 26/02/14.
 */

@ExposesResourceFor(Pfc.class)
@RestController
@RequestMapping(value = "/pfcs", produces = MediaType.APPLICATION_JSON_VALUE)
public class PfcControllerRest implements PfcController {

    private PfcService pfcService;

    @Inject
    PfcControllerRest(PfcService pfcService) {
        this.pfcService = pfcService;
    }

    @Override
    public List<Pfc> getPfcs() {
        return pfcService.getAll();
    }

    @Override
    public Pfc getPfc(@PathVariable long pfcId) {
        return pfcService.findById(pfcId);
    }


    @Override
    public Pfc createPfc(@RequestBody Pfc pfc) {
        return pfcService.createPfc(pfc.getNombre(), pfc.getDepartamento(), pfc.getEstado(), new ArrayList<Long>());
    }

    @Override
    public List<Pfc> searchPfcs(@RequestParam String departamento, @RequestParam String nombre, @RequestParam EstadoPfc estado) {

        List<Pfc> pfcList = new ArrayList<>();
        if (nombre != null) {
            pfcList.addAll(pfcService.findByName(nombre));
        }
        else {
            if (departamento != null) {
                pfcList.addAll(pfcService.findByDepartamento(departamento));
            }
            else {
                pfcList.addAll(pfcService.findByEstado(estado));
            }
        }
        return pfcList;
    }


    @Override
    public Pfc removePfc(@PathVariable long pfcId) {
        return pfcService.deletePfc(pfcId);
    }

    @Override
    public Pfc updateNombre(@PathVariable long pfcId, @RequestParam String nombre) {
        return pfcService.updateNombre(pfcId, nombre);
    }

    @Override
    public Pfc updateEstado(@PathVariable long pfcId, @RequestParam EstadoPfc estado) {
        return pfcService.updateEstado(pfcId, estado);
    }

    @Override
    public Profesor getDirectorAcademico(@PathVariable long pfcId) {
        return  pfcService.findByDirectorAcademico(pfcId);
    }

    @Override
    public Pfc updateFechaFin(@PathVariable long pfcId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        return pfcService.updateFechaFin(pfcId, fechaFin);
    }

    @Override
    public Profesor changeDirectorAcademico(@PathVariable long pfcId, @RequestParam long profesor) {
        return pfcService.changeDirectorAcademico(pfcId, profesor);
    }

    @Override
    public List<Profesor> getDirectores(@PathVariable long pfcId) {
        return pfcService.findByDirector(pfcId);
    }

    @Override
    public List<Profesor> addDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores) {
        return pfcService.addDirectors(pfcId, directores);
    }

    @Override
    public List<Profesor> deleteDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores) {
        return pfcService.deleteDirectors(pfcId, directores);
    }
}
