package com.uma.informatica.persistence.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "profesor")
public class Profesor {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="dni", unique = true, nullable = false, length = 9)
    private String dni;

    @Column(name="nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column(name = "titulacion", nullable = false, length = TitulacionEnum.MAX_LONGITUD_TITULACION)
    private TitulacionEnum titulacion;

    @Column(name = "empresa", length = 80)
    private String empresa;

    @Column(name = "cargo", length = 60)
    private String cargo;

    @Column(name = "direccionEmpresa", length = 100)
    private String direccionEmpresa;

    @Column(name = "telefono", nullable = false, length = 12)
    private String telefono;

    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @JsonIgnore
    @ManyToMany(mappedBy = "directores")
    private Collection<Pfc> pfcs;

    public Profesor() {}

    public Profesor(String dni, String nombre, String apellidos, TitulacionEnum titulacion, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.titulacion = titulacion;
        this.telefono = telefono;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public TitulacionEnum getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(TitulacionEnum titulacion) {
        this.titulacion = titulacion;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Pfc> getPfcs() {
        return pfcs;
    }

    public void setPfcs(Collection<Pfc> pfcs) {
        this.pfcs = pfcs;
    }

}
