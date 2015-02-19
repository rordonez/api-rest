package com.uma.informatica.controllers.resources;

import com.uma.informatica.persistence.models.Profesor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class ProfesorResource extends Resource<Profesor> {

	public ProfesorResource(Profesor profesor) {
		super(profesor);
	}

	public ProfesorResource(Profesor content, Link... links) {
		super(content, links);
	}

}
