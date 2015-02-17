package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.controllers.resources.PfcResource;
import com.uma.informatica.controllers.resources.ProfesorResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa on 16/11/14.
 */
@Component
public class PfcResourceAssembler extends ResourceAssemblerSupport<Pfc, PfcResource> {

    private EntityLinks entityLinks;

    @Autowired
    public PfcResourceAssembler(EntityLinks entityLinks) {
        super(PfcControllerRest.class, PfcResource.class);
        this.entityLinks = entityLinks;
    }
    
    @Override
    public PfcResource toResource(Pfc pfc) {

        PfcResource pfcResource = new PfcResource(pfc, entityLinks.linkToSingleResource(pfc));

        if(pfc.getDirectorAcademico() != null) {
            pfcResource.setDirectorAcademico(new ProfesorResource(entityLinks.linkToSingleResource(pfc.getDirectorAcademico())));
        }

        if(pfc.getDirectores() != null && !pfc.getDirectores().isEmpty()) {

            List<ProfesorResource> directores = new ArrayList<ProfesorResource>();
            for(Profesor director : pfc.getDirectores()) {
                directores.add(new ProfesorResource(entityLinks.linkToSingleResource(director)));
            }
            pfcResource.setDirectores(directores);
        }

        return pfcResource;
    }
    
    @Override
    public PfcResource instantiateResource(Pfc pfc) {
    	return new PfcResource(pfc);
    }

}
