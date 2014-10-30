package com.uma.informatica.controllers;

import com.uma.informatica.controllers.beans.DireccionRequestBody;
import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by rafaordonez on 02/03/14.
 */
@RequestMapping(value = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AlumnoController {


    @RequestMapping (method = RequestMethod.GET)
    List<Alumno> getAlumnos();


    @RequestMapping (method = RequestMethod.PUT)
    Alumno createAlumno(@Valid @RequestBody Alumno alumno);


    @RequestMapping (method = RequestMethod.POST)
    List<Alumno> searchAlumnos(@Valid @RequestBody SearchAlumnoRequestBody search);


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}")
    Alumno getAlumno(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}")
    Alumno removeAlumno(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/direccion")
    Alumno updateDireccion(@PathVariable long alumnoId, @Valid @RequestBody DireccionRequestBody direccion);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/email")
    Alumno updateEmail(@PathVariable long alumnoId, @NotNull @RequestParam String email);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/telefono")
    Alumno updateTelefono(@PathVariable long alumnoId, @NotNull @RequestParam String telefono);


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}/pfc")
    Pfc getPfc(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/pfc")
    Pfc addPfc(@PathVariable long alumnoId, @Valid @RequestBody Pfc pfc);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}/pfc")
    Pfc deletePfc(@PathVariable long alumnoId);

}