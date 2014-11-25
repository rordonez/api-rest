package com.uma.informatica.controllers.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.uma.informatica.controllers.ProfesorController;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.resources.ProfesorResource;

public class ProfesorResourceAssembler extends ResourceAssemblerSupport<Profesor, ProfesorResource>{

    private static final String pfcsRel = "pfcs";

	public ProfesorResourceAssembler() {
    	super(ProfesorController.class, ProfesorResource.class);
    }
    
    @Override
    public ProfesorResource toResource(Profesor profesor) {
        long profesorId = profesor.getId();
        ProfesorResource profesorResource = new ProfesorResource(profesor);
        Link selfLink = linkTo(methodOn(ProfesorController.class).getProfesor(profesorId)).withSelfRel();
        Link pfcsLink = linkTo(methodOn(ProfesorController.class).getPfcs(profesorId)).withRel(pfcsRel);

        profesorResource.add(selfLink);
        profesorResource.add(pfcsLink);

        return profesorResource;
    }
    
    @Override
    public ProfesorResource instantiateResource(Profesor profesor) {
    	return new ProfesorResource(profesor);
    }

}
