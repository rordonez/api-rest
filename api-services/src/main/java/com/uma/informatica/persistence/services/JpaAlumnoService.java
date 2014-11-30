package com.uma.informatica.persistence.services;

import com.uma.informatica.persistence.exceptions.AlumnoNoEncontradoException;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import com.uma.informatica.persistence.repositories.AlumnoRepository;
import com.uma.informatica.persistence.repositories.PfcRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class JpaAlumnoService implements AlumnoService {

    private AlumnoRepository alumnoRepository;
    private PfcRepository pfcRepository;

    @Inject
    public JpaAlumnoService(AlumnoRepository alumnoRepository, PfcRepository pfcRepository) {
        this.alumnoRepository = alumnoRepository;
        this.pfcRepository = pfcRepository;
    }

    @Override
    public Collection<Alumno> getAll() {
        Collection<Alumno> alumnos = (Collection<Alumno>) alumnoRepository.findAll();
        if(alumnos.isEmpty()) {
            throw new AlumnoNoEncontradoException();
        }
        return alumnos;
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

    /**
     * Este método también devuelve aquellos alumnos que no tienen asignado un Pfc en caso de que no encuentre el pfcId
     *
     * @param pfcId
     * @return
     */
    @Override
    public List<Alumno> findByPfc(long pfcId) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        return this.alumnoRepository.findByPfc(pfc);
    }

    @Override
    public Collection<Alumno> search(String dni, String nombre, String apellidos) {
        Collection<Alumno> alumnos = this.alumnoRepository.search(dni, nombre, apellidos);
        if (alumnos.isEmpty()) {
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
        Alumno alumno = alumnoRepository.findOne(alumnId);
        if (alumno == null) {
            throw new AlumnoNoEncontradoException(alumnId);
        }
        alumnoRepository.delete(alumnId);
        return alumno;
    }

    @Override
    public Alumno updateDireccion(long id, String domicilio, String localidad, String pais, String codigoPostal) {
        Alumno alumno = this.alumnoRepository.findOne(id);
        if (alumno == null) {
            throw new AlumnoNoEncontradoException(id);
        }
        alumno.setDomicilio(domicilio);
        alumno.setCodigoPostal(codigoPostal);
        alumno.setLocalidad(localidad);
        alumno.setPais(pais);
        this.alumnoRepository.save(alumno);

        return alumno;
    }

    @Override
    public Alumno updateEmail(long id, String email) {
        Alumno alumno = this.alumnoRepository.findOne(id);
        if (alumno == null) {
            throw new AlumnoNoEncontradoException(id);
        }
        alumno.setEmail(email);
        return this.alumnoRepository.save(alumno);
    }

    @Override
    public Alumno updateTelefono(long id, String telefono) {
        Alumno alumno = this.alumnoRepository.findOne(id);
        if (alumno == null) {
            throw new AlumnoNoEncontradoException(id);
        }
        alumno.setTelefono(telefono);
        return this.alumnoRepository.save(alumno);
    }

    @Override
    public Alumno createAlumno(String dni, String nombre, String apellidos, String titulacion, String domicilio, String localidad, String pais, String codigoPostal, String telefono, String email, Date fechaNacimiento) {
        Alumno alumno = new Alumno(dni, nombre, apellidos, TitulacionEnum.valueOf(titulacion), domicilio, localidad, pais, codigoPostal, telefono, email, fechaNacimiento);
        this.alumnoRepository.save(alumno);
        return alumno;
    }

}
