package com.uma.informatica.persistence.services;

import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by rafaordonez on 04/01/14.
 */
public interface PfcService {

    Pfc findById(long pfcId);

    List<Pfc> findByDepartamento(String departamentoId);

    List<Pfc> findByName(String nombre);

    List<Pfc> findByEstado(EstadoPfc estado);

    List<Pfc> getAll();

    Profesor findByDirectorAcademico(long pfcId);

    @Query("SELECT p FROM Profesor p INNER JOIN b.categories c WHERE c IN (:categories)")
    List<Profesor> findByDirector(long pfcId);

    Pfc createPfc(String nombre, String departamento, EstadoPfc estadoPfc, List<Long> directores);

    Pfc deletePfc(long pfcId);

    Pfc updateNombre(long pfcId, String nombre);

    Pfc updateEstado(long pfcId, EstadoPfc estado);

    Profesor changeDirectorAcademico(long pfcId, long directorAcademico);

    List<Profesor> deleteDirectors(long pfcId, List<Long> directores);

    List<Profesor> addDirectors(long pfcId, List<Long> directores);

    Pfc getPfcFromAlumno(long alumnoId);

    Pfc addPfcToAlumno(long alumnoId, String nombre, String departamento);

    Pfc deletePfcFromAlumno(long alumnoId);

    Pfc updateFechaFin(long pfcId, Date fechaFin);

}
