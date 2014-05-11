package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.services.PfcService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaordonez on 26/02/14.
 */

@Controller
@RequestMapping(value = "/pfcs", produces = MediaType.APPLICATION_JSON_VALUE)
public class PfcControllerRest implements PfcController {

    private PfcService pfcService;

    @Inject
    PfcControllerRest(PfcService pfcService) {
        this.pfcService = pfcService;
    }

    @Override
    public List<Pfc> getPfcs() {
        List<Pfc> pfcs = new ArrayList<>();
        pfcs.addAll(pfcService.getAll());
        return pfcs;
    }

    @Override
    public Pfc getPfc(@PathVariable Long pfcId) {
        return pfcService.findById(pfcId);
    }


    @Override
    public Pfc createPfc(@RequestBody Pfc pfc) {
        return pfcService.createPfc(pfc.getNombre(), pfc.getDepartamento(), pfc.getFechaInicio(), pfc.getEstado());
    }



    @Override
    public Pfc removePfc(@PathVariable Long pfcId) {
        return pfcService.deletePfc(pfcId);
    }

}
