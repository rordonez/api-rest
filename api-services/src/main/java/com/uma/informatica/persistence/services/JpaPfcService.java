package com.uma.informatica.persistence.services;

import com.uma.informatica.persistence.exceptions.PfcNoEncontradoException;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.persistence.repositories.PfcRepository;
import com.uma.informatica.persistence.repositories.ProfesorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by rafaordonez on 04/01/14.
 */
@Service
@Transactional
public class JpaPfcService implements PfcService {


    private PfcRepository pfcRepository;
    private ProfesorRepository profesorRepository;

    @Inject
    public JpaPfcService(PfcRepository pfcRepository, ProfesorRepository profesorRepository) {
        this.pfcRepository = pfcRepository;
        this.profesorRepository = profesorRepository;
    }

    @Override
    public List<Pfc> findByDepartamento(String departamentId) {
        return this.pfcRepository.findByDepartamento(departamentId, new PageRequest(0, 1)).getContent();
    }

    @Override
    public Pfc findById(Long pfcId) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        if (pfcId == null) {
            throw new PfcNoEncontradoException(pfcId);
        }
        return pfc;
    }



    @Override
    public List<Pfc> getAll() {
        return this.pfcRepository.findAll();
    }

    @Override
    public Pfc findByName(String name) {
        return this.pfcRepository.findByNombre(name);
    }

    @Override
    public List<Pfc> findByEstado(String estado) {
        return this.pfcRepository.findByEstado(EstadoPfc.valueOf(estado), new PageRequest(0, 1)).getContent();
    }

    @Override
    public Pfc createPfc(String nombre, String departamento, Date fechaInicio, EstadoPfc estadoPfc) {
        Pfc pfc = new Pfc(nombre, departamento, fechaInicio, estadoPfc);

        return this.pfcRepository.save(pfc);
    }

    @Override
    public Pfc deletePfc(Long pfcId) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        this.pfcRepository.delete(pfcId);
        return pfc;
    }

    @Override
    public Pfc updateNombre(Long pfcId, String nombre) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        pfc.setNombre(nombre);

        return this.pfcRepository.save(pfc);
    }

    @Override
    public Pfc deleteDirector(Long pfcId, Long director) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        Profesor profesor = this.profesorRepository.findOne(director);
        pfc.getDirectores().remove(profesor);
        this.pfcRepository.save(pfc);
        return pfc;
    }

    @Override
    public Pfc addDirector(Long pfcId, Long director) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        Profesor profesor = this.profesorRepository.findOne(director);
        pfc.getDirectores().add(profesor);
        return this.pfcRepository.save(pfc);
    }

    @Override
    public Pfc updateEstado(Long pfcId, String estado) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        pfc.setEstado(EstadoPfc.valueOf(estado));
        return this.pfcRepository.save(pfc);
    }

    @Override
    public Pfc addDirectorAcademico(Long pfcId, Long directorAcademico) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        Profesor profesor = this.profesorRepository.findOne(directorAcademico);
        pfc.setDirectorAcademico(profesor);
        return this.pfcRepository.save(pfc);
    }

    @Override
    public Pfc changeDirectorAcademico(Long pfcId, Long directorAcademico) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);
        Profesor profesor = this.profesorRepository.findOne(directorAcademico);
        pfc.setDirectorAcademico(profesor);
        return this.pfcRepository.save(pfc);
    }
}
