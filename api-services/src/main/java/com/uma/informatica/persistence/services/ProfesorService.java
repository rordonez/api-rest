package com.uma.informatica.persistence.services;


import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;

import java.util.Collection;
import java.util.List;

/**
 * Created by rafaordonez on 07/01/14.
 */
public interface ProfesorService {

    Collection<Pfc> search(long profesorId, String token);

    Profesor findById(Long id);

    Profesor findByDni(String dni);

    List<Profesor> findByNombre(String nombre);

    List<Profesor> findByNombreYApellidos(String name, String lastName);

    List<Profesor> getAll();

    Profesor createProfesor(String dni, String name, String apellidos, TitulacionEnum titulacion, String telefono, String email);

    Profesor deleteProfesor(Long profesorId);

    Profesor updateTelefono(Long profesorId, String telefono);

    Profesor updateEmail(Long profesorId, String email);

    List<Profesor> getPfcDirectors(Long pfcId);

    Profesor findByEmail(String email);

    Profesor updateEmpresa(long profesorId, String empresa);

    Profesor addEmpresa(long profesorId, String empresa);

    Profesor addTelefono(long profesorId, String telefono);

    Profesor addEmail(long profesorId, String email);

    List<Pfc> getPfcs(long profesorId);
}
