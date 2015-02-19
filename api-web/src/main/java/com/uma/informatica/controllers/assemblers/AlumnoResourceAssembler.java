package com.uma.informatica.controllers.assemblers;

/**
 * Created by rafa on 19/02/15.
 */

import com.uma.informatica.controllers.AlumnoControllerRest;
import com.uma.informatica.controllers.resources.AlumnoResource;
import com.uma.informatica.persistence.models.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;


/**
 * Created by rafa on 16/11/14.
 */
@Component
public class AlumnoResourceAssembler extends ResourceAssemblerSupport<Alumno, AlumnoResource> {

    private EntityLinks entityLinks;


    @Autowired
    public AlumnoResourceAssembler(EntityLinks entityLinks) {
        super(AlumnoControllerRest.class, AlumnoResource.class);
        this.entityLinks = entityLinks;
    }


    @Override
    public AlumnoResource toResource(Alumno alumno) {
        AlumnoResource alumnoResource = new AlumnoResource(alumno, entityLinks.linkToSingleResource(alumno));

        return alumnoResource;
    }

    @Override
    public AlumnoResource instantiateResource(Alumno alumno) {
        return new AlumnoResource(alumno);
    }
}
