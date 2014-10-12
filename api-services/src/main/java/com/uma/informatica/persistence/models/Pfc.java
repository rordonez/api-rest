package com.uma.informatica.persistence.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uma.informatica.core.jackson.date.DateFormatPatterns;
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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= DateFormatPatterns.YYYY_MM_DD_HH_00, timezone="CET")
    @Column(name = "fechaInicio")
    private Date fechaInicio;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= DateFormatPatterns.YYYY_MM_DD, timezone="CET")
    @Column(name = "fechaFin")
    private Date fechaFin;

    @JoinColumn(name = "directorAcademico")
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

    public Pfc(String nombre, String departamento, EstadoPfc estado, List<Profesor> directores) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.estado = estado;
        this.directores = directores;
    }

    public Pfc(String nombre, String departamento) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.estado = EstadoPfc.NO_EMPEZADO;
        this.directores = Collections.<Profesor>emptyList();
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

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}