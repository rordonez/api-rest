package com.uma.informatica.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.resources.AlumnoResource;

/**
 * Created by rafa on 16/11/14.
 */
@Component
public class AlumnoResourceAssembler extends ResourceAssemblerSupport<Alumno, AlumnoResource> {

    public AlumnoResourceAssembler() {
		super(AlumnoController.class, AlumnoResource.class);
	}

	private String pfcRel = "pfc";

    @Override
    public AlumnoResource toResource(Alumno alumno) {
        long alumnoId = alumno.getId();
        alumno.setPfc(null);
        AlumnoResource alumnoResource = new AlumnoResource(alumno);
        Link selfLink = linkTo(methodOn(AlumnoControllerRest.class).getAlumno(alumnoId)).withSelfRel();
        Link pfcLink = linkTo(methodOn(AlumnoControllerRest.class).getPfc(alumnoId)).withRel(pfcRel);

        alumnoResource.add(selfLink);
        alumnoResource.add(pfcLink);
        return alumnoResource;
    }

    @Override
    public AlumnoResource instantiateResource(Alumno alumno) {
    	return new AlumnoResource(alumno);
    }
}
