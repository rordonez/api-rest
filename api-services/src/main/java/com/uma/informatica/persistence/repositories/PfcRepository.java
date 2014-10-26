package com.uma.informatica.persistence.repositories;


import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Repository for dealing with {@link Pfc pfc} records.
 */

@RestResource(path = "pfcs", rel = "pfcs")
public interface PfcRepository extends PagingAndSortingRepository<Pfc, Long> {

    List<Pfc> findAll();

    Page<Pfc> findByDepartamento(@Param("departamento") String departamento, Pageable pageable);

//    Page<Pfc> findByAlumno(@Param("alumnId") String alumnId, Pageable pageable);
//
//    List<Pfc> findByAlumno(@Param("alumnId") String alumnId);

    Page<Pfc> findByNombre(@Param("nombre") String nombre, Pageable pageable);

    Page<Pfc> findByEstado(@Param("estado") EstadoPfc estado, Pageable pageable);

    List<Profesor> findByDirectores(@Param("directores") String directores);

    Profesor findByDirectorAcademico(long pfcId);

    @Query("SELECT p FROM Profesor p INNER JOIN p.pfcs d WHERE d.id = :pfcId")
    List<Profesor> findByDirector(@Param("pfcId") long pfcId);
}
