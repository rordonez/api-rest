package com.uma.informatica.persistence.services;


import com.uma.informatica.persistence.models.Profesor;

import java.util.List;

/**
 * Created by rafaordonez on 07/01/14.
 */
public interface ProfesorService {

    Profesor findById(Long id);

    Profesor findByDni(String dni);

    List<Profesor> findProfesorByName(String name);

    List<Profesor> findProfesorByFirstNameOrLastName(String name, String lastName);

    List<Profesor> getAll();

    Profesor createProfesor(String dni, String name, String apellidos, String titulacion, String telefono, String email);

    Profesor deleteProfesor(Long profesorId);

    Profesor updateTelefono(Long profesorId, String telefono);

    Profesor updateEmail(Long profesorId, String email);

    List<Profesor> getPfcDirectors(Long pfcId);

}
