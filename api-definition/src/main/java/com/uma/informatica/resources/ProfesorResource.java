package com.uma.informatica.resources;

import org.springframework.hateoas.Resource;

import com.uma.informatica.persistence.models.Profesor;

public class ProfesorResource extends Resource<Profesor> {

	public ProfesorResource(Profesor profesor) {
		super(profesor);
	}
}
