package com.uma.informatica.controllers.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uma.informatica.persistence.models.Pfc;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import java.util.List;

public class PfcResource extends Resource<Pfc> {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ProfesorResource> directores;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private ProfesorResource directorAcademico;

	public PfcResource(Pfc pfc) {
		super(pfc);
	}

	public PfcResource(Link link) {
		super(new Pfc(), link);
	}

	public PfcResource(Pfc pfc, Link link) {
		super(pfc, link);
	}

	public List<ProfesorResource> getDirectores() {
		return directores;
	}

	public void setDirectores(List<ProfesorResource> directores) {
		this.directores = directores;
	}

	public ProfesorResource getDirectorAcademico() {
		return directorAcademico;
	}

	public void setDirectorAcademico(ProfesorResource directorAcademico) {
		this.directorAcademico = directorAcademico;
	}
}
