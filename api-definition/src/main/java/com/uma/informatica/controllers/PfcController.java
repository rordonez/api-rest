package com.uma.informatica.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.resources.PfcResource;
import com.uma.informatica.resources.ProfesorResource;

/**
 * Created by rafaordonez on 05/03/14.
 */
@RequestMapping(value = "/pfcs", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PfcController {


    @RequestMapping (method = RequestMethod.GET)
    ResponseEntity<Resources<PfcResource>> getPfcs();


    @RequestMapping (method = RequestMethod.PUT)
    ResponseEntity<PfcResource> createPfc(@RequestBody Pfc pfc);


    @RequestMapping (method = RequestMethod.GET, value = "/search")
    ResponseEntity<Resources<PfcResource>> searchPfcs(@RequestParam String departamento, @RequestParam String nombre, @RequestParam EstadoPfc estado);


    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}")
    ResponseEntity<PfcResource> getPfc(@PathVariable long pfcId);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{pfcId}")
    ResponseEntity<PfcResource> removePfc(@PathVariable long pfcId);


    @RequestMapping (method = RequestMethod.POST, value = "/{pfcId}/nombre")
    ResponseEntity<PfcResource> updateNombre(@PathVariable long pfcId, @RequestParam String nombre);


    @RequestMapping (method = RequestMethod.POST, value = "/{pfcId}/estado")
    ResponseEntity<PfcResource> updateEstado(@PathVariable long pfcId, @RequestParam EstadoPfc estado);


    @RequestMapping (method = RequestMethod.POST, value = "/{pfcId}/fechafin")
    ResponseEntity<PfcResource> updateFechaFin(@PathVariable long pfcId, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaFin);


    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}/directores_academicos")
    ResponseEntity<ProfesorResource> getDirectorAcademico(@PathVariable long pfcId);


    @RequestMapping (method = RequestMethod.PUT, value = "/{pfcId}/directores_academicos")
    ResponseEntity<ProfesorResource> changeDirectorAcademico(@PathVariable long pfcId, @RequestBody long profesor);


    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}/directores")
    ResponseEntity<Resources<ProfesorResource>> getDirectores(@PathVariable long pfcId);


    @RequestMapping (method = RequestMethod.PUT, value = "/{pfcId}/directores")
    ResponseEntity<Resources<ProfesorResource>> addDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{pfcId}/directores")
    ResponseEntity<Resources<ProfesorResource>> deleteDirectores(@PathVariable long pfcId, @RequestParam List<Long> directores);
}
