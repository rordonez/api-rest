package com.uma.informatica.persistence.repositories;


import com.uma.informatica.persistence.models.Profesor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import java.util.List;

/**
 * Repository for dealing with {@link Profesor profesor} records.
 */
@RestResource(path = "profesors", rel = "profesors")
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Profesor findByDni(@Param("dni") String dni);

    Profesor findByEmail(@Param("email") String email);

    List<Profesor> findByNombre(@Param("nombre") String nombre, @Param("sort") Sort.Direction sort);

    List<Profesor> findByNombreOrApellidos(@Param("nombre") String nombre, @Param("apellidos") String apellidos);

}

