package com.uma.informatica.persistence.services;


import com.uma.informatica.core.exceptions.AlumnoNoEncontradoException;
import com.uma.informatica.persistence.models.Alumno;

import java.util.Collection;
import java.util.Date;


public interface AlumnoService {

    Alumno findById(long alumnoId) throws AlumnoNoEncontradoException;

    Alumno findByDni(String dni);

    Collection<Alumno> getAll();

    Collection<Alumno> search(String dni, String nombre, String apellidos);

    Collection<Alumno> findByNombre(String nombre);

    Collection<Alumno> findByNombreYApellidos(String nombre, String apellidos);

    Alumno createAlumno(String dni, String nombre, String apellidos, String titulacion, String domicilio, String localidad, String pais, String codigoPostal, String telefono, String email, Date fechaNacimiento);

    Alumno deleteAlumno(long alumnId);

    Alumno updateAlumno(long id, String domicilio, String localidad, String pais, String codigoPostal, String email, String telefono);

    void deleteAll();

    Alumno deletePfc(long alumnoId);
}
