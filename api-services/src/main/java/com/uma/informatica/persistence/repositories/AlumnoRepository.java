package com.uma.informatica.persistence.repositories;

import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "SELECT a from Alumno a where a.dni like CONCAT('%',:dni,'%') or a.nombre like CONCAT('%',:nombre,'%') or a.apellidos like CONCAT('%',:apellidos,'%')")
    List<Alumno> search(@Param("dni") String dni, @Param("nombre") String nombre, @Param("apellidos") String apellidos);

    List<Alumno> findByNombre(@Param("nombre") String nombre);

    List<Alumno> findByNombreOrApellidos(@Param("nombre") String nombre, @Param("apellidos") String apellidos);


}