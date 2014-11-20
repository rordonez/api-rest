package com.uma.informatica.resources;

import org.springframework.hateoas.Resource;

import com.uma.informatica.persistence.models.Pfc;

public class PfcResource extends Resource<Pfc> {

	public PfcResource(Pfc pfc) {
		super(pfc);
	}
	

}
