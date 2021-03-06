package com.uma.informatica.controllers;

import com.uma.informatica.controllers.assemblers.PfcResourceAssembler;
import com.uma.informatica.controllers.assemblers.ProfesorResourceAssembler;
import com.uma.informatica.controllers.resources.PfcResource;
import com.uma.informatica.controllers.resources.ProfesorResource;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.persistence.services.PfcService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.uma.informatica.controllers.utils.ControllerUtils.allows;

/**
 * Created by rafaordonez on 26/02/14.
 */

@ExposesResourceFor(Pfc.class)
@RestController
@Api(value = "pfcs", description = "Api para PFC")
@RequestMapping(value = "/pfcs")
public class PfcControllerRest {

    private PfcService pfcService;

    private final PfcResourceAssembler pfcResourceAssembler;
    private final ProfesorResourceAssembler profesorResourceAssembler;

    @Autowired
    PfcControllerRest(PfcService pfcService, PfcResourceAssembler pfcResourceAssembler, ProfesorResourceAssembler profesorResourceAssembler) {
        this.pfcService = pfcService;
        this.pfcResourceAssembler = pfcResourceAssembler;
        this.profesorResourceAssembler = profesorResourceAssembler;
    }

    @RequestMapping (method = RequestMethod.GET)
    public ResponseEntity<PagedResources<PfcResource>> getPfcs(@PageableDefault(size = 10) Pageable pageable, PagedResourcesAssembler<Pfc> pagedAssembler) {
        PagedResources<PfcResource> pfcsResources = pagedAssembler.toResource(pfcService.getAll(pageable), pfcResourceAssembler);

        return new ResponseEntity<> (pfcsResources, HttpStatus.OK);
    }


    @RequestMapping (method = RequestMethod.GET, params = {"search=true"})
    public ResponseEntity<PagedResources<PfcResource>> searchPfcs(@PageableDefault(size = 10) Pageable pageable, PagedResourcesAssembler<Pfc> pagedAssembler, @RequestParam(required = false) String departamento, @RequestParam(required = false) String nombre, @RequestParam(required = false) EstadoPfc estado) {
        PagedResources<PfcResource> pfcsResources = pagedAssembler.toResource(pfcService.search(departamento, nombre, estado != null ? estado.name() : null, pageable), pfcResourceAssembler);
        return new ResponseEntity<> (pfcsResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.POST)
    public ResponseEntity<PfcResource> createPfc(@Valid @RequestBody Pfc pfc) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.createPfc(pfc.getNombre(), pfc.getDepartamento())), HttpStatus.CREATED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> pfcsOptions() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }

    @RequestMapping (method = RequestMethod.GET, value = "/{pfcId}")
    public ResponseEntity<PfcResource> getPfc(@PathVariable long pfcId) {
    	return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.findById(pfcId)), HttpStatus.OK);
    }


    @RequestMapping (method = RequestMethod.DELETE, value = "/{pfcId}")
    public ResponseEntity<PfcResource> removePfc(@PathVariable long pfcId) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.deletePfc(pfcId)), HttpStatus.ACCEPTED);
    }

    @RequestMapping (method = RequestMethod.PATCH, value = "/{pfcId}")
    public ResponseEntity<PfcResource> updatePfc(@PathVariable long pfcId, @NotNull @RequestBody Pfc pfc) {
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfcService.updatePfc(pfcId, pfc.getNombre(), pfc.getDepartamento(), pfc.getFechaInicio(), pfc.getFechaFin(), pfc.getEstado())),HttpStatus.ACCEPTED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS, value = "/{pfcId}")
    public ResponseEntity<Void> pfcOptions() {
        return allows(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.OPTIONS);
    }


    @RequestMapping (method = RequestMethod.DELETE, value = "/{pfcId}/directoracademico")
    public ResponseEntity<ProfesorResource> deleteDirectorAcademico(@PathVariable long pfcId) {
        return new ResponseEntity<>(profesorResourceAssembler.toResource(pfcService.deleteDirectorAcademico(pfcId)), HttpStatus.ACCEPTED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS, value = "/{pfcId}/directoracademico")
    public ResponseEntity<Void> directorAcademicoOptions() {
        return allows(HttpMethod.DELETE, HttpMethod.OPTIONS);
    }



    @RequestMapping (method = RequestMethod.PUT, value = "/{pfcId}/directoracademico/{profesorId}")
    public ResponseEntity<ProfesorResource> changeDirectorAcademico(@PathVariable long pfcId, @PathVariable long profesorId) {
        return new ResponseEntity<>(profesorResourceAssembler.toResource(pfcService.changeDirectorAcademico(pfcId, profesorId)), HttpStatus.ACCEPTED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS, value = "/{pfcId}/directoracademico/{profesorId}")
    public ResponseEntity<Void> directorAcademicoSeleccionadoOptions() {
        return allows(HttpMethod.PUT, HttpMethod.OPTIONS);
    }



    @RequestMapping (method = RequestMethod.PUT, value = "/{pfcId}/directores")
    public ResponseEntity<Resources<ProfesorResource>> addDirectores(@PathVariable long pfcId, @Size @RequestParam("directores") List<Long> directores) {
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfcService.addDirectors(pfcId, directores)));

        return new ResponseEntity<> (profesoresResources, HttpStatus.CREATED);
    }

    @RequestMapping (method = RequestMethod.DELETE, value = "/{pfcId}/directores")
    public ResponseEntity<Resources<ProfesorResource>> deleteDirectores(@PathVariable long pfcId) {
    	Resources<ProfesorResource> profesoresResources = new Resources<ProfesorResource>(profesorResourceAssembler.toResources(pfcService.deleteDirectors(pfcId)));

        return new ResponseEntity<> (profesoresResources, HttpStatus.ACCEPTED);
    }

    @RequestMapping (method = RequestMethod.OPTIONS, value = "/{pfcId}/directores")
    public ResponseEntity<Void> directoresOptions() {
        return allows(HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS);
    }
}
