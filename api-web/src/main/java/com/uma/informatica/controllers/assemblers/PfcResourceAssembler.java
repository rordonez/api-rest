package com.uma.informatica.controllers.assemblers;

import com.uma.informatica.controllers.PfcControllerRest;
import com.uma.informatica.controllers.resources.PfcResource;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa on 16/11/14.
 */
@Component
public class PfcResourceAssembler extends ResourceAssemblerSupport<Pfc, PfcResource> {

    public static final String DIRECTOR_ACADEMICO_REL = "directorAcademico";
    public static final String DIRECTORES_REL = "directores";

    private EntityLinks entityLinks;

    @Autowired
    public PfcResourceAssembler(EntityLinks entityLinks) {
        super(PfcControllerRest.class, PfcResource.class);
        this.entityLinks = entityLinks;
    }
    
    @Override
    public PfcResource toResource(Pfc pfc) {

        PfcResource pfcResource = new PfcResource(pfc, entityLinks.linkToSingleResource(Pfc.class, pfc.getId()));

        addDirectorAcademicoLink(pfc, pfcResource);
        addDirectoresLink(pfc, pfcResource);

        return pfcResource;
    }

    private void addDirectorAcademicoLink(Pfc pfc, PfcResource pfcResource) {
        if(pfc.getDirectorAcademico() != null) {
            pfcResource.add(entityLinks.linkToSingleResource(pfc.getDirectorAcademico()).withRel(DIRECTOR_ACADEMICO_REL));
        }
    }

    private void addDirectoresLink(Pfc pfc, PfcResource pfcResource) {
        if(pfc.getDirectores() != null && !pfc.getDirectores().isEmpty()) {

            List<Long> ids = new ArrayList<Long>();
            for(Profesor director : pfc.getDirectores()) {
                ids.add(director.getId());
            }

            pfcResource.add( new Link(UriComponentsBuilder.fromUri(entityLinks.linkFor(Profesor.class).toUri()).queryParam("ids", ids.toArray()).build().toUriString()).withRel(DIRECTORES_REL));
        }
    }

    @Override
    public PfcResource instantiateResource(Pfc pfc) {
    	return new PfcResource(pfc);
    }

}
