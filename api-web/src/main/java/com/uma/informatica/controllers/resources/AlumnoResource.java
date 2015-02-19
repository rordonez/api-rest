package com.uma.informatica.controllers.resources;

import com.uma.informatica.persistence.models.Alumno;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class AlumnoResource extends Resource<Alumno> {


	public AlumnoResource(Alumno alumno) {
		super(alumno);
	}

	public AlumnoResource(Alumno alumno, Link link) {
		super(alumno, link);
	}

}
