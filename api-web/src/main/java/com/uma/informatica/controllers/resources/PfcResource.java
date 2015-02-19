package com.uma.informatica.controllers.resources;

import com.uma.informatica.persistence.models.Pfc;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class PfcResource extends Resource<Pfc> {

	public PfcResource(Pfc pfc) {
		super(pfc);
	}

	public PfcResource(Pfc pfc, Link link) {
		super(pfc, link);
	}

}
