package com.uma.informatica.persistence.services;

import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.enums.EstadoPfc;

import java.util.Date;
import java.util.List;

/**
 * Created by rafaordonez on 04/01/14.
 */
public interface PfcService {

    Pfc findById(Long pfcId);

    List<Pfc> findByDepartamento(String departamentId);

    Pfc findByName(String name);

    List<Pfc> findByEstado(String estado);

    List<Pfc> getAll();

    Pfc createPfc(String nombre, String departamento, Date fechaInicio, EstadoPfc estadoPfc);

    Pfc deletePfc(Long pfcId);

    Pfc updateNombre(Long pfcId, String nombre);

    Pfc updateEstado(Long pfcId, String estado);

    Pfc addDirectorAcademico(Long pfcId, Long directorAcademico);

    Pfc changeDirectorAcademico(Long pfcId, Long directorAcademico);

    Pfc deleteDirector(Long pfcId, Long director);

    Pfc addDirector(Long pfcId, Long director);

}
