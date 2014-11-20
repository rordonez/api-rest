package com.uma.informatica.controllers.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.uma.informatica.controllers.PfcController;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.resources.PfcResource;

/**
 * Created by rafa on 16/11/14.
 */
@Component
public class PfcResourceAssembler extends ResourceAssemblerSupport<Pfc, PfcResource> {

    private String directorAcademicoRel = "directorAcademico";
    private String directoresRel = "directores";

    public PfcResourceAssembler() {
    	super(PfcController.class, PfcResource.class);
    }
    
    @Override
    public PfcResource toResource(Pfc pfc) {
        long pfcId = pfc.getId();
        pfc.setDirectores(null);
        PfcResource pfcResource = new PfcResource(pfc);
        Link selfLink = linkTo(methodOn(PfcController.class).getPfc(pfcId)).withSelfRel();
        Link directorAcademicoLink = linkTo(methodOn(PfcController.class).getDirectorAcademico(pfcId)).withRel(directorAcademicoRel);
        Link directoresLink = linkTo(methodOn(PfcController.class).getDirectores(pfcId)).withRel(directoresRel);

        pfcResource.add(selfLink);
        pfcResource.add(directorAcademicoLink);
        pfcResource.add(directoresLink);

        return pfcResource;
    }
    
    @Override
    public PfcResource instantiateResource(Pfc pfc) {
    	return new PfcResource(pfc);
    }

}
