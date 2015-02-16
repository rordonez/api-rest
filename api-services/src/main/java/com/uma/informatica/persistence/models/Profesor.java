package com.uma.informatica.persistence.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "profesor")
public class Profesor implements Identifiable<Long>, Serializable{

	private static final long serialVersionUID = -6839445993695967602L;

    @JsonIgnore
	@Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name="dni", unique = true, nullable = false, length = 9)
    private String dni;

    @NotNull
    @Column(name="nombre", nullable = false, length = 45)
    private String nombre;

    @NotNull
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

    @NotNull
    @Column(name = "telefono", nullable = false, length = 12)
    private String telefono;

    @NotNull
    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "directores")
    private Collection<Pfc> pfcs;

    @PreRemove
    private void removePfcsFromProfesores() {
        for (Pfc pfc : pfcs) {

        }
    }

    public Profesor() {}

    public Profesor(String dni, String nombre, String apellidos, TitulacionEnum titulacion, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.titulacion = titulacion;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profesor profesor = (Profesor) o;

        if (!apellidos.equals(profesor.apellidos)) return false;
        if (cargo != null ? !cargo.equals(profesor.cargo) : profesor.cargo != null) return false;
        if (direccionEmpresa != null ? !direccionEmpresa.equals(profesor.direccionEmpresa) : profesor.direccionEmpresa != null)
            return false;
        if (!dni.equals(profesor.dni)) return false;
        if (email != null ? !email.equals(profesor.email) : profesor.email != null) return false;
        if (empresa != null ? !empresa.equals(profesor.empresa) : profesor.empresa != null) return false;
        if (!nombre.equals(profesor.nombre)) return false;
        if (telefono != null ? !telefono.equals(profesor.telefono) : profesor.telefono != null) return false;
        if (titulacion != profesor.titulacion) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dni.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + apellidos.hashCode();
        result = 31 * result + (titulacion != null ? titulacion.hashCode() : 0);
        result = 31 * result + (empresa != null ? empresa.hashCode() : 0);
        result = 31 * result + (cargo != null ? cargo.hashCode() : 0);
        result = 31 * result + (direccionEmpresa != null ? direccionEmpresa.hashCode() : 0);
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
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
