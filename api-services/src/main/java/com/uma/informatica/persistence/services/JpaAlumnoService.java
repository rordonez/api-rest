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
        return (Collection<Alumno>) alumnoRepository.findAll();
    }

    @Override
    public Alumno findById(Long id) throws AlumnoNoEncontradoException {
        Alumno alumno = this.alumnoRepository.findOne(id);
        if(alumno == null) {
            throw new AlumnoNoEncontradoException(id);
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
    public List<Alumno> findByPfc(Long pfcId) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        return this.alumnoRepository.findByPfc(pfc);
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
    public Alumno deleteAlumno(Long alumnId) {
        Alumno alumno = alumnoRepository.findOne(alumnId);
        alumnoRepository.delete(alumnId);
        return alumno;
    }

    @Override
    public Alumno updateDireccion(Long id, String domicilio, String localidad, String pais, String codigoPostal) {
        Alumno alumno = this.alumnoRepository.findOne(id);
        alumno.setDomicilio(domicilio);
        alumno.setCodigoPostal(codigoPostal);
        alumno.setLocalidad(localidad);
        alumno.setPais(pais);
        this.alumnoRepository.save(alumno);

        return alumno;
    }

    @Override
    public Alumno updateEmail(Long id, String email) {
        Alumno alumno = this.alumnoRepository.findOne(id);
        alumno.setEmail(email);
        return this.alumnoRepository.save(alumno);
    }

    @Override
    public Alumno updateTelefono(Long id, String telefono) {
        Alumno alumno = this.alumnoRepository.findOne(id);
        alumno.setTelefono(telefono);
        return this.alumnoRepository.save(alumno);
    }

    @Override
    public Alumno createAlumno(String dni, String nombre, String apellidos, String titulacion, String domicilio, String localidad, String pais, String codigoPostal, String telefono, String email, Date fechaNacimiento) {
        Alumno alumno = new Alumno(dni, nombre, apellidos, TitulacionEnum.valueOf(titulacion), domicilio, localidad, pais, codigoPostal, telefono, email, fechaNacimiento);
        this.alumnoRepository.save(alumno);
        return alumno;
    }

    @Override
    public Alumno addPfc(Long id, Long pfcId) {
        Alumno alumno = this.alumnoRepository.findOne(id);
        alumno.setPfc(this.pfcRepository.findOne(pfcId));
        return this.alumnoRepository.save(alumno);
    }

    @Override
    public Alumno deletePfc(Long id) {
        Alumno alumno = this.alumnoRepository.findOne(id);
        alumno.setPfc(null);
        return this.alumnoRepository.save(alumno);

    }


}
