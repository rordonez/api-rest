package com.uma.informatica.persistence.services;


import com.uma.informatica.persistence.exceptions.AlumnoNoEncontradoException;
import com.uma.informatica.persistence.models.Alumno;

import java.util.Collection;
import java.util.Date;
import java.util.List;


public interface AlumnoService {

    List<Alumno> findByPfc(Long pfcId);

    Alumno findById(Long id) throws AlumnoNoEncontradoException;

    Alumno findByDni(String dni);

    Collection<Alumno> getAll();

    Collection<Alumno> findByNombre(String nombre);

    Collection<Alumno> findByNombreYApellidos(String nombre, String apellidos);

    Alumno createAlumno(String dni, String nombre, String apellidos, String titulacion, String domicilio, String localidad, String pais, String codigoPostal, String telefono, String email, Date fechaNacimiento);

    Alumno deleteAlumno(Long alumnId);

    Alumno updateDireccion(Long id, String domicilio, String localidad, String pais, String codigoPostal);

    Alumno updateEmail(Long id, String email);

    Alumno updateTelefono(Long id, String telefono);

    Alumno addPfc(Long id, Long pfcId);

    Alumno deletePfc(Long id);
}
