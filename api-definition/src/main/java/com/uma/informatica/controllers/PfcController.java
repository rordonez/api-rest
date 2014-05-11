package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Pfc;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by rafaordonez on 05/03/14.
 */
@RequestMapping(value = "/pfcs", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PfcController {

    @RequestMapping (method = RequestMethod.GET)
    @ResponseBody
    List<Pfc> getPfcs();


    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}")
    @ResponseBody
    Pfc getPfc(@PathVariable Long pfcId);


    @RequestMapping (method = RequestMethod.POST)
    @ResponseBody
    Pfc createPfc(@RequestBody Pfc pfc);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{pfcId}")
    @ResponseBody
    Pfc removePfc(@PathVariable Long pfcId);

}
