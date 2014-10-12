package com.uma.informatica.persistence.services;

import com.google.common.collect.Lists;
import com.uma.informatica.persistence.exceptions.*;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.persistence.repositories.AlumnoRepository;
import com.uma.informatica.persistence.repositories.PfcRepository;
import com.uma.informatica.persistence.repositories.ProfesorRepository;
import com.uma.informatica.persistence.repositories.ProfesorSpecifications;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by rafaordonez on 04/01/14.
 */
@Service
@Transactional
public class JpaPfcService implements PfcService {

    private AlumnoRepository alumnoRepository;
    private PfcRepository pfcRepository;
    private ProfesorRepository profesorRepository;

    @Inject
    public JpaPfcService(AlumnoRepository alumnoRepository, PfcRepository pfcRepository, ProfesorRepository profesorRepository) {
        this.alumnoRepository = alumnoRepository;
        this.pfcRepository = pfcRepository;
        this.profesorRepository = profesorRepository;
    }



    @Override
    public List<Pfc> findByDepartamento(String departamentoId) {
        return this.pfcRepository.findByDepartamento(departamentoId, new PageRequest(0, 1)).getContent();
    }

    @Override
    public Pfc findById(long pfcId) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        if (pfc == null) {
            throw new PfcNoEncontradoException(pfcId);
        }
        return pfc;
    }

    @Override
    public List<Pfc> getAll() {
        return this.pfcRepository.findAll();
    }

    @Override
    public List<Pfc> findByName(String name) {
        return this.pfcRepository.findByNombre(name, new PageRequest(0, 1)).getContent();
    }

    @Override
    public List<Pfc> findByEstado(EstadoPfc estado) {
        return this.pfcRepository.findByEstado(estado, new PageRequest(0, 1)).getContent();
    }

    @Override
    public Profesor findByDirectorAcademico(long pfcId) {
        return this.pfcRepository.findByDirectorAcademico(pfcId);
    }

    @Override
    public List<Profesor> findByDirector(long pfcId) {
        return this.pfcRepository.findByDirector(pfcId);
    }

    @Override
    public Pfc createPfc(String nombre, String departamento, EstadoPfc estadoPfc, List<Long> directores) {
        if (CollectionUtils.isEmpty(directores)) {
            throw new InvalidPfcDataCreationException("No se puede crear un Pfc si no tiene directores asociados");
        }
        Iterable<Profesor> profesores = this.profesorRepository.findAll(ProfesorSpecifications.idInList(directores));
        Pfc pfc = new Pfc(nombre, departamento, estadoPfc, Lists.newArrayList(profesores));

        return this.pfcRepository.save(pfc);
    }

    @Override
    public Pfc deletePfc(long pfcId) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        if(pfc == null) {
            throw new PfcNoEncontradoException(pfcId);
        }
        this.pfcRepository.delete(pfcId);
        return pfc;
    }

    @Override
    public Pfc updateNombre(long pfcId, String nombre) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        if (pfc == null) {
            throw new PfcNoEncontradoException(pfcId);
        }
        pfc.setNombre(nombre);

        return this.pfcRepository.save(pfc);
    }

    @Override
    public List<Profesor> deleteDirectors(long pfcId, List<Long> directores) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        if (pfc == null) {
            throw new PfcNoEncontradoException(pfcId);
        }
        List<Profesor> profesores = Lists.newArrayList(this.profesorRepository.findAll(directores));
        if(profesores== null || profesores.isEmpty()) {
            throw new ProfesorNoEncontradoException(directores);
        }
        pfc.getDirectores().removeAll(profesores);
        this.pfcRepository.save(pfc);
        return profesores;
    }

    @Override
    public List<Profesor> addDirectors(long pfcId, List<Long> directores) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        if (pfc == null) {
            throw new PfcNoEncontradoException(pfcId);
        }
        List<Profesor> profesores = Lists.newArrayList(this.profesorRepository.findAll(directores));
        if(profesores == null || profesores.isEmpty()) {
            throw new ProfesorNoEncontradoException(directores);
        }
        pfc.setDirectores(profesores);
        this.pfcRepository.save(pfc);
        return profesores;
    }

    @Override
    public Pfc updateEstado(long pfcId, EstadoPfc estado) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        if (pfc == null) {
            throw new PfcNoEncontradoException(pfcId);
        }
        pfc.setEstado(estado);
        return this.pfcRepository.save(pfc);
    }

    @Override
    public Profesor changeDirectorAcademico(long pfcId, long directorAcademico) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        if (pfc == null) {
            throw new PfcNoEncontradoException(pfcId);
        }
        Profesor profesor = this.profesorRepository.findOne(directorAcademico);
        if(profesor == null) {
            throw new ProfesorNoEncontradoException(directorAcademico);
        }
        pfc.setDirectorAcademico(profesor);
        this.pfcRepository.save(pfc);
        return profesor;
    }

    @Override
    public Pfc getPfcFromAlumno(long alumnoId) {
        Alumno alumno = this.alumnoRepository.findOne(alumnoId);
        if(alumno == null) {
            throw new AlumnoNoEncontradoException(alumnoId);
        }
        if(alumno.getPfc() == null) {
            throw new AlumnoSinPfcException(alumnoId);
        }
        return alumno.getPfc();
    }

    @Override
    public Pfc addPfcToAlumno(long alumnoId, String nombre, String departamento) {
        Alumno alumno = this.alumnoRepository.findOne(alumnoId);
        if(alumno == null) {
            throw new AlumnoNoEncontradoException(alumnoId);
        }
        Pfc pfc = new Pfc(nombre, departamento);
        alumno.setPfc(pfc);
        return this.pfcRepository.save(pfc);
    }

    @Override
    public Pfc deletePfcFromAlumno(long alumnoId) {
        Alumno alumno = this.alumnoRepository.findOne(alumnoId);
        if(alumno == null) {
            throw new AlumnoNoEncontradoException(alumnoId);
        }
        if(alumno.getPfc() == null) {
            throw new AlumnoSinPfcException(alumnoId);
        }
        Pfc pfc = this.pfcRepository.findOne(alumno.getPfc().getId());
        alumno.setPfc(null);
        this.alumnoRepository.save(alumno);
        this.pfcRepository.delete(pfc.getId());
        return pfc;
    }

    @Override
    public Pfc updateFechaFin(long pfcId, Date fechaFin) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        if (pfc == null) {
            throw new PfcNoEncontradoException(pfcId);
        }
        pfc.setFechaFin(fechaFin);
        return this.pfcRepository.save(pfc);
    }

}
