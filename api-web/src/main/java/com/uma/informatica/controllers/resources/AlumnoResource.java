package com.uma.informatica.controllers.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uma.informatica.persistence.models.Alumno;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class AlumnoResource extends Resource<Alumno> {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private PfcResource pfc;

	public AlumnoResource(Alumno alumno) {
		super(alumno);
	}

	public AlumnoResource(Alumno alumno, Link link) {
		super(alumno, link);
	}

	public PfcResource getPfc() {
		return pfc;
	}

	public void setPfc(PfcResource pfc) {
		this.pfc = pfc;
	}
}
