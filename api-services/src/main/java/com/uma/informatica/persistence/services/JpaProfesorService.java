package com.uma.informatica.persistence.services;

import com.uma.informatica.persistence.exceptions.ProfesorNoEncontradoException;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import com.uma.informatica.persistence.repositories.PfcRepository;
import com.uma.informatica.persistence.repositories.ProfesorRepository;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by rafaordonez on 07/01/14.
 */
@Service
@Transactional
public class JpaProfesorService implements ProfesorService {

    private ProfesorRepository profesorRepository;
    private PfcRepository pfcRepository;

    @Inject
    public JpaProfesorService(ProfesorRepository profesorRepository, PfcRepository pfcRepository) {
        this.profesorRepository = profesorRepository;
        this.pfcRepository = pfcRepository;
    }



    @Override
    public Collection<Pfc> search(long profesorId, String token) {
        return this.profesorRepository.search(profesorId, "%" + token + "%");    }


    @Override
    public Profesor findByDni(String dni) {
        return this.profesorRepository.findByDni(dni);
    }

    @Override
    public Profesor findById(Long id) {
        return this.profesorRepository.findOne(id);
    }

    @Override
    public List<Profesor> findByNombre(String name) {
        return this.profesorRepository.findByNombre(name, Sort.Direction.ASC);
    }

    @Override
    public List<Profesor> findByNombreYApellidos(String name, String lastName) {
        return this.profesorRepository.findByNombreAndApellidos(name, lastName);
    }

    @Override
    public List<Profesor> getAll() {
        return this.profesorRepository.findAll();
    }

    @Override
    public Profesor createProfesor(String dni, String name, String apellidos, TitulacionEnum titulacion, String telefono, String email) {
        Profesor p = new Profesor(dni, name, apellidos, titulacion, telefono, email);
        return this.profesorRepository.save(p);
    }

    @Override
    public Profesor deleteProfesor(Long profesorId) {
        Profesor profesor = this.profesorRepository.findOne(profesorId);

        this.profesorRepository.delete(profesorId);
        return  profesor;
    }

    @Override
    public Profesor updateTelefono(Long profesorId, String telefono) {
        Profesor profesor = this.profesorRepository.findOne(profesorId);
        profesor.setTelefono(telefono);
        return this.profesorRepository.save(profesor);
    }

    @Override
    public Profesor updateEmail(Long profesorId, String email) {
        Profesor profesor = this.profesorRepository.findOne(profesorId);
        profesor.setEmail(email);
        return this.profesorRepository.save(profesor);
    }

    @Override
    public List<Profesor> getPfcDirectors(Long pfcId) {
        Pfc pfc = this.pfcRepository.findOne(pfcId);

        ArrayList<Profesor> profesors = new ArrayList<>();
        for (Profesor p : pfc.getDirectores()) {
            Hibernate.initialize(p);
            profesors.add(p);
        }
        return Collections.unmodifiableList(profesors);
    }

    @Override
    public Profesor findByEmail(String email) {
        return this.profesorRepository.findByEmail(email);
    }

    @Override
    public Profesor updateEmpresa(long profesorId, String empresa) {
        Profesor profesor = this.profesorRepository.findOne(profesorId);
        if (profesor == null) {
            throw new ProfesorNoEncontradoException(profesorId);
        }
        profesor.setEmpresa(empresa);

        return this.profesorRepository.save(profesor);
    }

    @Override
    public Profesor addEmpresa(long profesorId, String empresa) {
        Profesor profesor = this.profesorRepository.findOne(profesorId);
        if (profesor == null) {
            throw new ProfesorNoEncontradoException(profesorId);
        }
        profesor.setEmpresa(empresa);
        return profesor;
    }

    @Override
    public Profesor addTelefono(long profesorId, String telefono) {
        Profesor profesor = this.profesorRepository.findOne(profesorId);
        if (profesor == null) {
            throw new ProfesorNoEncontradoException(profesorId);
        }
        profesor.setTelefono(telefono);
        return profesor;
    }

    @Override
    public Profesor addEmail(long profesorId, String email) {
        Profesor profesor = this.profesorRepository.findOne(profesorId);
        if (profesor == null) {
            throw new ProfesorNoEncontradoException(profesorId);
        }
        profesor.setEmail(email);
        return profesor;
    }

    @Override
    public List<Pfc> getPfcs(long profesorId) {
        Profesor profesor = this.profesorRepository.findOne(profesorId);
        if (profesor == null) {
            throw new ProfesorNoEncontradoException(profesorId);
        }
        return (List<Pfc>) profesor.getPfcs();
    }
}
