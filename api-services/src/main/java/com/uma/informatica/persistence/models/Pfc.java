package com.uma.informatica.persistence.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.uma.informatica.core.jackson.date.DateFormatPatterns;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "pfc")
public class Pfc implements Identifiable<Long>, Serializable {

	private static final long serialVersionUID = -4371465104392056259L;

    @JsonIgnore
	@Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;

    @Column(name = "fechaInicio")
    private Date fechaInicio;

    @Column(name = "fechaFin")
    private Date fechaFin;

    @JsonIgnore
    @JoinColumn(name = "directorAcademico")
    @OneToOne(optional=true, fetch = FetchType.EAGER)
	private Profesor directorAcademico;

    @Column(name = "estado", nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private EstadoPfc estado;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "PFCS_PROFESORES",
            joinColumns = @JoinColumn(name = "pfc_id"),
            inverseJoinColumns = @JoinColumn(name = "profesor_id"))
    private List<Profesor> directores = new ArrayList<>();

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder(113, 123).append(this.id).append(this.nombre).append(this.departamento).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        Pfc p = (Pfc) o;
        return new EqualsBuilder().append(((Pfc) o).departamento, this.departamento).append(p.estado, this.estado)
                .append(p.fechaFin, this.fechaFin).append(p.fechaInicio, this.fechaInicio).append(p.nombre, this.nombre)
                .isEquals();
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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= DateFormatPatterns.yyyy_MM_dd, timezone="CET")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= DateFormatPatterns.yyyy_MM_dd, timezone="CET")
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