package com.uma.informatica.persistence.services;

import com.google.common.collect.Lists;
import com.uma.informatica.core.exceptions.*;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.persistence.repositories.AlumnoRepository;
import com.uma.informatica.persistence.repositories.PfcRepository;
import com.uma.informatica.persistence.repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    @Autowired
    public JpaPfcService(AlumnoRepository alumnoRepository, PfcRepository pfcRepository, ProfesorRepository profesorRepository) {
        this.alumnoRepository = alumnoRepository;
        this.pfcRepository = pfcRepository;
        this.profesorRepository = profesorRepository;
    }

    @Override
    public Page<Pfc> search(String departamento, String nombre, String estado, Pageable pageable) {
        Page<Pfc> pfcs = this.pfcRepository.search(departamento, nombre, estado, pageable);
        if(pfcs.getTotalElements() == 0) {
            throw new PfcNoEncontradoException();
        }
        return pfcs;
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
    public Page<Pfc> getAll(Pageable pageable) {
        Page<Pfc> pfcs = this.pfcRepository.findAll(pageable);
        if(pfcs.getTotalElements() == 0) {
            throw new PfcNoEncontradoException();
        }
        return pfcs;
    }

    @Override
    public List<Pfc> findByName(String name) {
        return this.pfcRepository.findByNombre(name, new PageRequest(0, 1)).getContent();
    }

    @Override
    public List<Pfc> findByEstado(EstadoPfc estado) {
        return this.pfcRepository.findByEstado(estado, new PageRequest(0, 1)).getContent();
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
    public Profesor findByDirectorAcademico(long pfcId) {
        Pfc pfc =  this.findById(pfcId);
        if (pfc.getDirectorAcademico() == null) {
            throw new DirectorAcademicoNotFoundException(pfcId);
        }
        return pfc.getDirectorAcademico();
    }


    @Override
    public Pfc createPfc(String nombre, String departamento) {
        Pfc pfc = new Pfc(nombre, departamento);

        return this.pfcRepository.save(pfc);
    }

    @Override
    public Pfc deletePfc(long pfcId) {
        Pfc pfc = this.findById(pfcId);
        List<Alumno> alumnos = this.alumnoRepository.findByPfc(pfc);
        for(Alumno a : alumnos) {
            a.setPfc(null);
            this.alumnoRepository.save(a);
        }


        List<Profesor> profesores = pfc.getDirectores();
        for(Profesor p : profesores) {
            p.getPfcs().remove(pfc);
            this.profesorRepository.save(p);
        }

        pfc.setDirectores(null);

        this.pfcRepository.delete(pfcId);
        return pfc;
    }

    @Override
    public Pfc updatePfc(long pfcId, String nombre, String departamento, Date fechaInicio, Date fechaFin, EstadoPfc estado) {

        Pfc pfc = this.findById(pfcId);
        updateFields(nombre, departamento, fechaInicio, fechaFin, estado, pfc);

        this.pfcRepository.save(pfc);
        return pfc;
    }



    @Override
    public List<Profesor> deleteDirectors(long pfcId) {
        Pfc pfc = this.findById(pfcId);

        if(CollectionUtils.isEmpty(pfc.getDirectores())) {
            throw new PfcSinDirectoresException(pfcId);
        }

        List<Profesor> profesores = new ArrayList<>(pfc.getDirectores());

        for(Profesor profesor : pfc.getDirectores()) {
            profesor.getPfcs().remove(pfc);
            this.profesorRepository.save(profesor);
        }

        pfc.getDirectores().clear();
        this.pfcRepository.save(pfc);
        return profesores;
    }

    @Override
    public List<Profesor> addDirectors(long pfcId, List<Long> directores) {
        Pfc pfc = this.findById(pfcId);
        List<Profesor> profesores = Lists.newArrayList(this.profesorRepository.findAll(directores));
        if(profesores == null || profesores.isEmpty()) {
            throw new ProfesorNoEncontradoException(directores);
        }
        pfc.setDirectores(profesores);
        this.pfcRepository.save(pfc);
        return profesores;
    }


    @Override
    public Profesor changeDirectorAcademico(long pfcId, long directorAcademico) {
        Pfc pfc = this.findById(pfcId);
        Profesor profesor = this.profesorRepository.findOne(directorAcademico);
        if(profesor == null) {
            throw new ProfesorNoEncontradoException(directorAcademico);
        }
        pfc.setDirectorAcademico(profesor);
        this.pfcRepository.save(pfc);
        return profesor;
    }


    @Override
    public Pfc addPfcToAlumno(long alumnoId, long pfcId) {
        Alumno alumno = this.alumnoRepository.findOne(alumnoId);
        if(alumno == null) {
            throw new AlumnoNoEncontradoException(alumnoId);
        }
        Pfc pfc = this.findById(pfcId);

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
        Pfc pfc = alumno.getPfc();
        alumno.setPfc(null);
        this.alumnoRepository.save(alumno);
        return pfc;
    }

    @Override
    public void deleteAll() {
        this.pfcRepository.deleteAll();
    }

    @Override
    public Profesor deleteDirectorAcademico(long pfcId) {
        Pfc pfc = this.findById(pfcId);
        Profesor profesor = pfc.getDirectorAcademico();
        if(profesor == null) {
            throw new ProfesorNoEncontradoException();
        }

        pfc.setDirectorAcademico(null);
        this.pfcRepository.save(pfc);

        return profesor;
    }

    private void updateFields(String nombre, String departamento, Date fechaInicio, Date fechaFin, EstadoPfc estado, Pfc pfc) {
        if(nombre != null) {
            pfc.setNombre(nombre);
        }
        if (departamento != null) {
            pfc.setDepartamento(departamento);
        }
        if(fechaInicio != null) {
            pfc.setFechaInicio(fechaInicio);
        }
        if(fechaFin != null) {
            pfc.setFechaFin(fechaFin);
        }
        if(estado != null) {
            pfc.setEstado(estado);
        }
    }
}
