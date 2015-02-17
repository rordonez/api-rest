package com.uma.informatica.controllers;

import com.uma.informatica.controllers.beans.APILinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rafa on 17/02/15.
 */
@ExposesResourceFor(APILinks.class)
@RestController
public class RootController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resource<APILinks>> getApiLinks() {
        Resource<APILinks> links = new Resource<APILinks>(new APILinks());

        links.add(ControllerLinkBuilder.linkTo(AlumnoControllerRest.class).withRel("alumnos"));
        links.add(ControllerLinkBuilder.linkTo(PfcControllerRest.class).withRel("pfcs"));
        links.add(ControllerLinkBuilder.linkTo(ProfesorControllerRest.class).withRel("profesores"));

        return new ResponseEntity<Resource<APILinks>>(links, HttpStatus.OK);
    }
}
