package com.uma.informatica.controllers.resources;

import javax.inject.Inject;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.uma.informatica.controllers.AlumnoController;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.resources.AlumnoResource;

/**
 * Created by rafa on 16/11/14.
 */
@Component
public class AlumnoResourceAssembler extends ResourceAssemblerSupport<Alumno, AlumnoResource> {

	
	private String pfcRel = "pfc";

	private EntityLinks entityLinks;
	
	
	@Inject
	public AlumnoResourceAssembler(EntityLinks entityLinks) {
		super(AlumnoController.class, AlumnoResource.class);
		this.entityLinks = entityLinks;
	}


    @Override
    public AlumnoResource toResource(Alumno alumno) {
        Pfc pfc = alumno.getPfc();

        alumno.setPfc(null);
        AlumnoResource alumnoResource = new AlumnoResource(alumno);
        alumnoResource.add(entityLinks.linkToSingleResource(alumno));
        if(pfc != null) {        	
        	alumnoResource.add(entityLinks.linkToSingleResource(pfc).withRel(pfcRel));
        }
        return alumnoResource;
    }

    @Override
    public AlumnoResource instantiateResource(Alumno alumno) {
    	return new AlumnoResource(alumno);
    }
}
