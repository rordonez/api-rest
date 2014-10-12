package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by rafaordonez on 05/03/14.
 */
@RequestMapping(value = "/pfcs", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PfcController {


    @RequestMapping (method = RequestMethod.GET)
    @ResponseBody
    List<Pfc> getPfcs();


    @RequestMapping (method = RequestMethod.PUT)
    @ResponseBody
    Pfc createPfc(@RequestBody Pfc pfc);


    @RequestMapping (method = RequestMethod.GET, value = "/search")
    @ResponseBody
    List<Pfc> searchPfcs(@RequestParam String departamento, @RequestParam String nombre, @RequestParam EstadoPfc estado);


    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}")
    @ResponseBody
    Pfc getPfc(@PathVariable long pfcId);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{pfcId}")
    @ResponseBody
    Pfc removePfc(@PathVariable long pfcId);


    @RequestMapping (method = RequestMethod.POST, value = "/{pfcId}/nombre")
    @ResponseBody
    Pfc updateNombre(@PathVariable long pfcId, @RequestParam String nombre);


    @RequestMapping (method = RequestMethod.POST, value = "/{pfcId}/estado")
    @ResponseBody
    Pfc updateEstado(@PathVariable long pfcId, @RequestParam EstadoPfc estado);


    @RequestMapping (method = RequestMethod.POST, value = "/{pfcId}/fechafin")
    @ResponseBody
    Pfc updateFechaFin(@PathVariable long pfcId, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaFin);


    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}/directores_academicos")
    @ResponseBody
    Profesor getDirectorAcademico(@PathVariable long pfcId);


    @RequestMapping (method = RequestMethod.PUT, value = "/{pfcId}/directores_academicos")
    @ResponseBody
    Profesor changeDirectorAcademico(@PathVariable long pfcId, @RequestBody long profesor);


    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}/directores")
    @ResponseBody
    List<Profesor> getDirectores(@PathVariable long pfcId);


    @RequestMapping (method = RequestMethod.PUT, value = "/{pfcId}/directores")
    @ResponseBody
    List<Profesor> addDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{pfcId}/directores")
    @ResponseBody
    List<Profesor> deleteDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores);
}
