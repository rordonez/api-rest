package com.uma.informatica.persistence.services;

import com.uma.informatica.core.exceptions.AlumnoNoEncontradoException;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import com.uma.informatica.persistence.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class JpaAlumnoService implements AlumnoService {

    private AlumnoRepository alumnoRepository;

    @Autowired
    public JpaAlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public Page<Alumno> getAll(Pageable pageable) {
        Page<Alumno> alumnosPage = alumnoRepository.findAll(pageable);
        if(alumnosPage.getTotalElements() == 0) {
            throw new AlumnoNoEncontradoException();
        }
        return alumnosPage;
    }

    @Override
    public Alumno findById(long alumnoId) throws AlumnoNoEncontradoException {
        Alumno alumno = this.alumnoRepository.findOne(alumnoId);
        if(alumno == null) {
            throw new AlumnoNoEncontradoException(alumnoId);
        }
        return alumno;
    }

    @Override
    public Alumno findByDni(String dni) {
        return this.alumnoRepository.findByDni(dni);
    }



    @Override
    public Page<Alumno> search(String dni, String nombre, String apellidos, Pageable pageable) {
        Page<Alumno> alumnos = this.alumnoRepository.search(dni, nombre, apellidos, pageable);
        if (alumnos.getTotalElements() == 0) {
            throw new AlumnoNoEncontradoException();
        }
        return alumnos;
    }

    @Override
    public Collection<Alumno> findByNombre(String nombre) {
        return this.alumnoRepository.findByNombre(nombre);
    }

    @Override
    public Collection<Alumno> findByNombreYApellidos(String nombre, String apellidos) {
        return this.alumnoRepository.findByNombreOrApellidos(nombre, apellidos);
    }

    @Override
    public Alumno deleteAlumno(long alumnId) {
        Alumno alumno = this.findById(alumnId);
        alumnoRepository.delete(alumnId);
        return alumno;
    }

    @Override
    public Alumno updateAlumno(long id, String domicilio, String localidad, String pais, String codigoPostal, String email, String telefono) {
        Alumno alumno = this.findById(id);
        if(domicilio != null)
            alumno.setDomicilio(domicilio);
        if(codigoPostal != null)
            alumno.setCodigoPostal(codigoPostal);
        if(localidad != null)
            alumno.setLocalidad(localidad);
        if(pais != null)
            alumno.setPais(pais);
        if(email != null)
            alumno.setEmail(email);
        if(telefono != null)
            alumno.setTelefono(telefono);

        this.alumnoRepository.save(alumno);

        return alumno;
    }


    @Override
    public Alumno createAlumno(String dni, String nombre, String apellidos, String titulacion, String domicilio, String localidad, String pais, String codigoPostal, String telefono, String email, Date fechaNacimiento) {
        Alumno alumno = new Alumno(dni, nombre, apellidos, TitulacionEnum.valueOf(titulacion), domicilio, localidad, pais, codigoPostal, telefono, email, fechaNacimiento);
        this.alumnoRepository.save(alumno);
        return alumno;
    }

    @Override
    public void deleteAll() {
        this.alumnoRepository.deleteAll();
    }

    @Override
    public Alumno deletePfc(long alumnoId) {
        Alumno alumno = this.findById(alumnoId);
        alumno.setPfc(null);
        return alumno;
    }
}
