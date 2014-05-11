package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rafaordonez on 05/03/14.
 */
@Controller
@RequestMapping(value = "/pfcs", produces = MediaType.APPLICATION_JSON_VALUE)
public class PfcControllerDoc implements PfcController {

    private List<Pfc> pfcs = new ArrayList<>();

    public PfcControllerDoc() {
        pfcs.add(new Pfc("Proyecto mock 1", "Departamento Ejemplo 1", new Date(), EstadoPfc.EMPEZADO));
        pfcs.add(new Pfc("Proyecto mock 2", "Departamento Ejemplo 2", new Date(), EstadoPfc.NO_EMPEZADO));
        pfcs.add(new Pfc("Proyecto mock 3", "Departamento Ejemplo 3", new Date(), EstadoPfc.EMPEZADO));
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Pfc> getPfcs() {
        return pfcs;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{pfcId}")
    @ResponseBody
    public Pfc getPfc(@PathVariable Long pfcId) {
        return pfcs.get(pfcId.intValue());
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Pfc createPfc(@RequestBody Pfc pfc) {
        pfcs.add(pfc);
        return pfc;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{pfcId}")
    @ResponseBody
    public Pfc removePfc(@PathVariable Long pfcId) {
        return pfcs.remove(pfcId.intValue());
    }

}