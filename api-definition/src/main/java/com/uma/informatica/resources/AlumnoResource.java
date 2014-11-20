package com.uma.informatica.resources;

import org.springframework.hateoas.Resource;

import com.uma.informatica.persistence.models.Alumno;

public class AlumnoResource extends Resource<Alumno> {

	public AlumnoResource(Alumno alumno) {
		super(alumno);
	}

}
