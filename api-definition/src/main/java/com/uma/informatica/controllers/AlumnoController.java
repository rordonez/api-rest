package com.uma.informatica.controllers;

import com.uma.informatica.controllers.beans.DireccionRequestBody;
import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by rafaordonez on 02/03/14.
 */
@RequestMapping(value = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AlumnoController {


    @RequestMapping (method = RequestMethod.GET)
    @ResponseBody
    List<Alumno> getAlumnos();


    @RequestMapping (method = RequestMethod.PUT)
    @ResponseBody
    Alumno createAlumno(@RequestBody Alumno alumno);


    @RequestMapping (method = RequestMethod.POST)
    @ResponseBody
    List<Alumno> searchAlumnos(@NotNull @RequestBody SearchAlumnoRequestBody search);


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}")
    @ResponseBody
    Alumno getAlumno(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}")
    @ResponseBody
    Alumno removeAlumno(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/direccion")
    @ResponseBody
    Alumno updateDireccion(@PathVariable long alumnoId, @NotNull @RequestBody DireccionRequestBody direccion);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/email")
    @ResponseBody
    Alumno updateEmail(@PathVariable long alumnoId, @NotNull @RequestParam String email);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/telefono")
    @ResponseBody
    Alumno updateTelefono(@PathVariable long alumnoId, @RequestParam String telefono);


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}/pfc")
    @ResponseBody
    Pfc getPfc(@PathVariable long alumnoId);


    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/pfc")
    @ResponseBody
    Pfc addPfc(@PathVariable long alumnoId, @RequestBody Pfc pfc);


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}/pfc")
    @ResponseBody
    Pfc deletePfc(@PathVariable long alumnoId);

}