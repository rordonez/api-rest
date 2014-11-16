package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Pfc;
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
public class PfcResourceAssembler implements ResourceAssembler<Pfc, Resource<Pfc>> {

    private String directorAcademicoRel = "directorAcademico";
    private String directoresRel = "directores";

    @Override
    public Resource<Pfc> toResource(Pfc pfc) {
        long pfcId = pfc.getId();
        pfc.setDirectores(null);
        Resource<Pfc> pfcResource = new Resource<Pfc>(pfc);
        Link selfLink = linkTo(methodOn(PfcControllerRest.class).getPfc(pfcId)).withSelfRel();
        Link directorAcademicoLink = linkTo(methodOn(PfcControllerRest.class).getDirectorAcademico(pfcId)).withRel(directorAcademicoRel);
        Link directoresLink = linkTo(methodOn(PfcControllerRest.class).getDirectores(pfcId)).withRel(directoresRel);

        pfcResource.add(selfLink);
        pfcResource.add(directorAcademicoLink);
        pfcResource.add(directoresLink);

        return pfcResource;
    }

}
