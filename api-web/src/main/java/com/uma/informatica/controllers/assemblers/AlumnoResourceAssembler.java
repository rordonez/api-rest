package com.uma.informatica.controllers.assemblers;

import com.uma.informatica.controllers.AlumnoControllerRest;
import com.uma.informatica.controllers.resources.AlumnoResource;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by rafa on 16/11/14.
 */
@Component
public class AlumnoResourceAssembler extends ResourceAssemblerSupport<Alumno, AlumnoResource> {

    public static final String PFC_REL = "pfc";
    private EntityLinks entityLinks;
	
	
	@Autowired
	public AlumnoResourceAssembler(EntityLinks entityLinks) {
		super(AlumnoControllerRest.class, AlumnoResource.class);
		this.entityLinks = entityLinks;
	}


    @Override
    public AlumnoResource toResource(Alumno alumno) {
        AlumnoResource alumnoResource = new AlumnoResource(alumno, entityLinks.linkToSingleResource(alumno));
        if(alumno.getPfc() != null && Hibernate.isInitialized(alumno.getPfc())) {
        	alumnoResource.add(entityLinks.linkToSingleResource(Pfc.class, alumno.getPfc().getId()).withRel(PFC_REL));
        }
        return alumnoResource;
    }

    @Override
    public AlumnoResource instantiateResource(Alumno alumno) {
    	return new AlumnoResource(alumno);
    }
}
