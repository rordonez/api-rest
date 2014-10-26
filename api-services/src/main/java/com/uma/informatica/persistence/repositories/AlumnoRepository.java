package com.uma.informatica.persistence.repositories;

import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


/**
 * Repository for dealing with {@link Alumno alumno} records.
 */

@RestResource(path = "alumns", rel = "alumns")
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

    List<Alumno> findByPfc(@Param("pfcId") Pfc pfcId);

    Alumno findByDni(@Param("dni") String dni);

    List<Alumno> findByNombre(@Param("nombre") String nombre);

    List<Alumno> findByNombreOrApellidos(@Param("nombre") String nombre, @Param("apellidos") String apellidos);


}