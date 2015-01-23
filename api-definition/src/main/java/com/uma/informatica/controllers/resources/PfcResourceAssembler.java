package com.uma.informatica.controllers.resources;

import com.uma.informatica.controllers.PfcController;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.resources.PfcResource;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa on 16/11/14.
 */
@Component
public class PfcResourceAssembler extends ResourceAssemblerSupport<Pfc, PfcResource> {

    private String directorAcademicoRel = "directorAcademico";
    private String directoresRel = "directores";

    private EntityLinks entityLinks;

    @Inject
    public PfcResourceAssembler(EntityLinks entityLinks) {
        super(PfcController.class, PfcResource.class);
        this.entityLinks = entityLinks;
    }
    
    @Override
    public PfcResource toResource(Pfc pfc) {
        Profesor directorAcademico = pfc.getDirectorAcademico();
        List<Profesor> directores = pfc.getDirectores();


//        pfc.setDirectores(null);
        pfc.setDirectorAcademico(null);

        PfcResource pfcResource = new PfcResource(pfc);
        pfcResource.add(entityLinks.linkToSingleResource(pfc));

        if(directorAcademico != null) {
            pfcResource.add(entityLinks.linkToSingleResource(directorAcademico).withRel(directorAcademicoRel));
        }
//        if(directores != null && !directores.isEmpty()) {
//            List<Link> directoresLinks = new ArrayList<>();
//            for(Profesor director : directores) {
//                directoresLinks.add(entityLinks.linkToSingleResource(director).withRel(directoresRel));
//            }
//            pfcResource.add(directoresLinks);
//        }

        return pfcResource;
    }
    
    @Override
    public PfcResource instantiateResource(Pfc pfc) {
    	return new PfcResource(pfc);
    }

}
