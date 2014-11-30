package com.uma.informatica.controllers;

import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.controllers.beans.UpdateAlumnoBody;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.resources.AlumnoResource;
import com.uma.informatica.resources.PfcResource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by rafaordonez on 02/03/14.
 */
@RequestMapping(value = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AlumnoController {


    @RequestMapping (method = RequestMethod.GET)
    ResponseEntity<Resources<AlumnoResource>> getAlumnos();


    @RequestMapping (method = RequestMethod.POST)
    ResponseEntity<Resources<AlumnoResource>> searchAlumnos(@Valid @RequestBody SearchAlumnoRequestBody search);


    @RequestMapping (method = RequestMethod.PUT)
    ResponseEntity<AlumnoResource> createAlumno(@Valid @RequestBody Alumno alumno);


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}")
    ResponseEntity<AlumnoResource> getAlumno(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}")
    ResponseEntity<AlumnoResource> removeAlumno(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}")
    ResponseEntity<AlumnoResource> updateAlumno(@PathVariable long alumnoId, @Valid @RequestBody UpdateAlumnoBody alumno);


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}/pfc")
    ResponseEntity<PfcResource> getPfc(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/pfc")
    ResponseEntity<PfcResource> addPfc(@PathVariable long alumnoId, @Valid @RequestBody Pfc pfc);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}/pfc")
    ResponseEntity<PfcResource> deletePfc(@PathVariable long alumnoId);

}