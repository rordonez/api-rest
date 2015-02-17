package com.uma.informatica.controllers.assemblers;

import com.uma.informatica.controllers.ProfesorControllerRest;
import com.uma.informatica.controllers.resources.ProfesorResource;
import com.uma.informatica.persistence.models.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class ProfesorResourceAssembler extends ResourceAssemblerSupport<Profesor, ProfesorResource>{

    private EntityLinks entityLinks;

    @Autowired
	public ProfesorResourceAssembler(EntityLinks entityLinks) {
    	super(ProfesorControllerRest.class, ProfesorResource.class);
        this.entityLinks = entityLinks;
    }
    
    @Override
    public ProfesorResource toResource(Profesor profesor) {
        ProfesorResource profesorResource = new ProfesorResource(profesor, entityLinks.linkToSingleResource(profesor));

        return profesorResource;
    }
    
    @Override
    public ProfesorResource instantiateResource(Profesor profesor) {
    	return new ProfesorResource(profesor);
    }

}
