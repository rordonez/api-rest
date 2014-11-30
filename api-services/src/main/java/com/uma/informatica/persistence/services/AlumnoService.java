package com.uma.informatica.persistence.services;


import com.uma.informatica.persistence.exceptions.AlumnoNoEncontradoException;
import com.uma.informatica.persistence.models.Alumno;

import java.util.Collection;
import java.util.Date;
import java.util.List;


public interface AlumnoService {

    List<Alumno> findByPfc(long pfcId);

    Alumno findById(long alumnoId) throws AlumnoNoEncontradoException;

    Alumno findByDni(String dni);

    Collection<Alumno> getAll();

    Collection<Alumno> search(String dni, String nombre, String apellidos);

    Collection<Alumno> findByNombre(String nombre);

    Collection<Alumno> findByNombreYApellidos(String nombre, String apellidos);

    Alumno createAlumno(String dni, String nombre, String apellidos, String titulacion, String domicilio, String localidad, String pais, String codigoPostal, String telefono, String email, Date fechaNacimiento);

    Alumno deleteAlumno(long alumnId);

    Alumno updateDireccion(long id, String domicilio, String localidad, String pais, String codigoPostal);

    Alumno updateEmail(long id, String email);

    Alumno updateTelefono(long id, String telefono);
}
