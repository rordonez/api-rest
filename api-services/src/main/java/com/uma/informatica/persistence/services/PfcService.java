package com.uma.informatica.persistence.services;

import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by rafaordonez on 04/01/14.
 */
public interface PfcService {

    Pfc findById(long pfcId);

    List<Pfc> findByDepartamento(String departamentoId);

    Page<Pfc> search(String departamento, String nombre, String estado, Pageable pageable);

    List<Pfc> findByName(String nombre);

    List<Pfc> findByEstado(EstadoPfc estado);

    Page<Pfc> getAll(Pageable pageable);

    Profesor findByDirectorAcademico(long pfcId);

    List<Alumno> findByPfc(long pfcId);

    Pfc createPfc(String nombre, String departamento);

    Pfc deletePfc(long pfcId);

    Pfc updatePfc(long pfcId, String nombre, String departamento, Date fechaInicio, Date fechaFin, EstadoPfc estado);

    Profesor changeDirectorAcademico(long pfcId, long directorAcademico);

    List<Profesor> deleteDirectors(long pfcId);

    List<Profesor> addDirectors(long pfcId, List<Long> directores);

    Pfc addPfcToAlumno(long alumnoId, long pfcId);

    Pfc deletePfcFromAlumno(long alumnoId);

    void deleteAll();

    Profesor deleteDirectorAcademico(long pfcId);
}
