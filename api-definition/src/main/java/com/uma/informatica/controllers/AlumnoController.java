package com.uma.informatica.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uma.informatica.controllers.beans.DireccionRequestBody;
import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;

/**
 * Created by rafaordonez on 02/03/14.
 */
@RequestMapping(value = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AlumnoController {


    @RequestMapping (method = RequestMethod.GET)
    ResponseEntity<Resources<Resource<Alumno>>> getAlumnos();


    @RequestMapping (method = RequestMethod.PUT)
    ResponseEntity<Resource<Alumno>> createAlumno(@Valid @RequestBody Alumno alumno);


    @RequestMapping (method = RequestMethod.POST)
    ResponseEntity<Resources<Resource<Alumno>>> searchAlumnos(@Valid @RequestBody SearchAlumnoRequestBody search);


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}")
    ResponseEntity<Resource<Alumno>> getAlumno(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}")
    ResponseEntity<Resource<Alumno>> removeAlumno(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/direccion")
    ResponseEntity<Resource<Alumno>> updateDireccion(@PathVariable long alumnoId, @Valid @RequestBody DireccionRequestBody direccion);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/email")
    ResponseEntity<Resource<Alumno>> updateEmail(@PathVariable long alumnoId, @NotNull @RequestParam String email);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/telefono")
    ResponseEntity<Resource<Alumno>> updateTelefono(@PathVariable long alumnoId, @NotNull @RequestParam String telefono);


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}/pfc")
    ResponseEntity<Resource<Pfc>> getPfc(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/pfc")
    ResponseEntity<Resource<Pfc>> addPfc(@PathVariable long alumnoId, @Valid @RequestBody Pfc pfc);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}/pfc")
    ResponseEntity<Resource<Pfc>> deletePfc(@PathVariable long alumnoId);

}