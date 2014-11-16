package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Alumno;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by rafa on 16/11/14.
 */
@Component
public class AlumnoResourceAssembler implements ResourceAssembler<Alumno, Resource<Alumno>> {


    private String pfcRel = "pfc";

    @Override
    public Resource<Alumno> toResource(Alumno alumno) {
        long alumnoId = alumno.getId();
        alumno.setPfc(null);
        Resource<Alumno> alumnoResource = new Resource<Alumno>(alumno);
        Link selfLink = linkTo(methodOn(AlumnoControllerRest.class).getAlumno(alumnoId)).withSelfRel();
        Link pfcLink = linkTo(methodOn(AlumnoControllerRest.class).getPfc(alumnoId)).withRel(pfcRel);

        alumnoResource.add(selfLink);
        alumnoResource.add(pfcLink);
        return alumnoResource;
    }
}
