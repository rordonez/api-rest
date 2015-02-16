package com.uma.informatica.controllers;

import com.uma.informatica.controllers.resources.ProfesorResource;
import com.uma.informatica.persistence.models.Profesor;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ProfesorResourceAssembler extends ResourceAssemblerSupport<Profesor, ProfesorResource>{

    private static final String pfcsRel = "pfcs";

    private EntityLinks entityLinks;

    @Inject
	public ProfesorResourceAssembler(EntityLinks entityLinks) {
    	super(ProfesorControllerRest.class, ProfesorResource.class);
        this.entityLinks = entityLinks;
    }
    
    @Override
    public ProfesorResource toResource(Profesor profesor) {
        long profesorId = profesor.getId();
        ProfesorResource profesorResource = new ProfesorResource(profesor, entityLinks.linkToSingleResource(profesor));

        return profesorResource;
    }
    
    @Override
    public ProfesorResource instantiateResource(Profesor profesor) {
    	return new ProfesorResource(profesor);
    }

}
