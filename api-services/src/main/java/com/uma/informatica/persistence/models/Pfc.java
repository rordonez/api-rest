package com.uma.informatica.persistence.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uma.informatica.persistence.models.enums.EstadoPfc;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pfc")
public class Pfc {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;

    @Column(name = "fechaInicio")
    private Date fechaInicio;

    @OneToOne(optional=true)
	private Profesor directorAcademico;

    @Column(name = "estado", nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private EstadoPfc estado;


    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "PFCS_PROFESORES",
            joinColumns = @JoinColumn(name = "pfc_id"),
            inverseJoinColumns = @JoinColumn(name = "profesor_id"))
    private List<Profesor> directores;

    public Pfc() {}

    public Pfc(String nombre, String departamento, Date fechaInicio, EstadoPfc estado) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.directores = Collections.EMPTY_LIST;
    }

    public Profesor getDirectorAcademico() {
        return directorAcademico;
    }

    public void setDirectorAcademico(Profesor directorAcademico) {
        this.directorAcademico = directorAcademico;
    }

    public List<Profesor> getDirectores() {
        return directores;
    }

    public void setDirectores(List<Profesor> directores) {
        this.directores = directores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public EstadoPfc getEstado() {
        return estado;
    }

    public void setEstado(EstadoPfc estado) {
        this.estado = estado;
    }

}