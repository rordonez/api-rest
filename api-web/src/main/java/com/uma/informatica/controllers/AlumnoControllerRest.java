package com.uma.informatica.controllers;

import com.uma.informatica.controllers.assemblers.AlumnoResourceAssembler;
import com.uma.informatica.controllers.assemblers.PfcResourceAssembler;
import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.controllers.beans.UpdateAlumnoBody;
import com.uma.informatica.controllers.resources.AlumnoResource;
import com.uma.informatica.controllers.resources.PfcResource;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.services.AlumnoService;
import com.uma.informatica.persistence.services.PfcService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.uma.informatica.controllers.utils.ControllerUtils.allows;

/**
 * Created by rafaordonez on 16/02/14.
 */

@ExposesResourceFor(Alumno.class)
@RestController
@Api(value = "alumnos", description = "Api para Alumnos")
@RequestMapping(value = "/alumnos")
public class AlumnoControllerRest  {

    private AlumnoService alumnoService;
    private PfcService pfcService;

    private final AlumnoResourceAssembler alumnoResourceAssembler;
    private final PfcResourceAssembler pfcResourceAssembler;

    @Autowired
    public AlumnoControllerRest(AlumnoService alumnoService, PfcService pfcService, AlumnoResourceAssembler alumnoResourceAssembler, PfcResourceAssembler pfcResourceAssembler) {
        this.alumnoService = alumnoService;
        this.pfcService = pfcService;
        this.alumnoResourceAssembler = alumnoResourceAssembler;
        this.pfcResourceAssembler = pfcResourceAssembler;
    }


    @RequestMapping (method = RequestMethod.GET)
    public ResponseEntity<PagedResources<AlumnoResource>> getAlumnos(@PageableDefault(size = 10) Pageable pageable, PagedResourcesAssembler<Alumno> pagedAssembler) {
        PagedResources<AlumnoResource> alumnosResources = pagedAssembler.toResource(alumnoService.getAll(pageable), alumnoResourceAssembler);

        return new ResponseEntity<> (alumnosResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.POST, params = {"search=true"})
    public ResponseEntity<PagedResources<AlumnoResource>> searchAlumnos(@PageableDefault(size = 10) Pageable pageable, PagedResourcesAssembler<Alumno> pagedAssembler, @Valid @RequestBody SearchAlumnoRequestBody body) {
        PagedResources<AlumnoResource> alumnosResources = pagedAssembler.toResource(alumnoService.search(body.getDni(), body.getNombre(), body.getApellidos(), pageable), alumnoResourceAssembler);

        return new ResponseEntity<> (alumnosResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.POST)
    public ResponseEntity<AlumnoResource> createAlumno(@Valid @RequestBody Alumno alumno) {
        AlumnoResource alumnoResource = alumnoResourceAssembler.toResource(alumnoService.createAlumno(alumno.getDni(), alumno.getNombre(), alumno.getApellidos(), alumno.getTitulacion().name(), alumno.getDomicilio(), alumno.getLocalidad(), alumno.getPais(), alumno.getCodigoPostal(), alumno.getTelefono(), alumno.getEmail(), alumno.getFechaNacimiento()));

        return new ResponseEntity<>(alumnoResource, HttpStatus.CREATED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> alumnosOptions() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }


    @RequestMapping (method = RequestMethod.GET, value = "/{alumnoId}")
    public ResponseEntity<AlumnoResource> getAlumno(@PathVariable long alumnoId) {
        AlumnoResource alumnoResource = alumnoResourceAssembler.toResource(alumnoService.findById(alumnoId));
        return new ResponseEntity<>(alumnoResource, HttpStatus.OK);
    }


    @RequestMapping (method = RequestMethod.PATCH, value = "/{alumnoId}")
    public ResponseEntity<AlumnoResource> updateAlumno(@PathVariable long alumnoId, @NotNull @Valid @RequestBody UpdateAlumnoBody alumno) {
        Alumno updatedAlumno = alumnoService.updateAlumno(alumnoId, alumno.getDomicilio(), alumno.getLocalidad(), alumno.getPais(), alumno.getCodigoPostal(), alumno.getEmail(), alumno.getTelefono());

        return new ResponseEntity<>(alumnoResourceAssembler.toResource(updatedAlumno), HttpStatus.ACCEPTED);
    }


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}")
    public ResponseEntity<AlumnoResource> removeAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumnoService.deleteAlumno(alumnoId)), HttpStatus.ACCEPTED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS, value = "/{alumnoId}")
    public ResponseEntity<Void> alumnoOptions(@PathVariable long alumnoId) {
        return allows(HttpMethod.GET, HttpMethod.PATCH, HttpMethod.DELETE, HttpMethod.OPTIONS);
    }


    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}/pfc")
    public ResponseEntity<PfcResource> deletePfc(@PathVariable long alumnoId) {
        PfcResource pfcResource = pfcResourceAssembler.toResource(pfcService.deletePfcFromAlumno(alumnoId));
        return new ResponseEntity<>(pfcResource, HttpStatus.ACCEPTED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS, value = "/{alumnoId}/pfc")
    public ResponseEntity<Void> alumnoPfcOptions(@PathVariable long alumnoId) {
        return allows(HttpMethod.DELETE, HttpMethod.OPTIONS);
    }


    @RequestMapping (method = RequestMethod.PUT, value = "/{alumnoId}/pfc/{pfcId}")
    public ResponseEntity<PfcResource> addPfc(@PathVariable long alumnoId, @PathVariable long pfcId) {
        PfcResource pfcResource = pfcResourceAssembler.toResource(pfcService.addPfcToAlumno(alumnoId, pfcId));
        return new ResponseEntity<>(pfcResource, HttpStatus.CREATED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS, value = "/{alumnoId}/pfc/{pfcId}")
    public ResponseEntity<Void> alumnosPfcOperacionesOptions(@PathVariable long alumnoId, @PathVariable long pfcId) {
        return allows(HttpMethod.PUT, HttpMethod.OPTIONS);
    }

}
