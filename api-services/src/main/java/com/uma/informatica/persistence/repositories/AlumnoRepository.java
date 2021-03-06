package com.uma.informatica.persistence.repositories;

import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repository for dealing with {@link Alumno alumno} records.
 */

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByPfc(@Param("pfcId") Pfc pfcId);

    Alumno findByDni(@Param("dni") String dni);

    @Query(value = "SELECT a from Alumno a where a.dni like CONCAT('%',:dni,'%') or a.nombre like CONCAT('%',:nombre,'%') or a.apellidos like CONCAT('%',:apellidos,'%')")
    Page<Alumno> search(@Param("dni") String dni, @Param("nombre") String nombre, @Param("apellidos") String apellidos, Pageable pageable);

    List<Alumno> findByNombre(@Param("nombre") String nombre);

    List<Alumno> findByNombreOrApellidos(@Param("nombre") String nombre, @Param("apellidos") String apellidos);


}