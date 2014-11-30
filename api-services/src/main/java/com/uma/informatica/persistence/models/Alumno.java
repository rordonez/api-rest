package com.uma.informatica.persistence.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uma.informatica.core.jackson.date.DateFormatPatterns;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "alumno")
public class Alumno implements Identifiable<Long>, Serializable {

	private static final long serialVersionUID = 8520931837010119598L;

	public static final int MAX_LONGITUD_NOMBRE = 45;
    public static final int MAX_LONGITUD_APELLIDOS = 100;
    public static final int MAX_LONGITUD_DOMICILIO = 80;
    public static final int MAX_LONGITUD_LOCALIDAD = 60;
    public static final int MAX_LONGITUD_PAIS = 45;
    private static final int MAX_LONGITUD_CODIGO_POSTAL = 5;
    private static final int MAX_LONGITUD_TELEFONO = 12;
    private static final int MAX_LONGITUD_EMAIL = 80;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "dni", unique = true, nullable = false, length = 9)
    private String dni;

    @ManyToOne( cascade = {CascadeType.ALL, CascadeType.REMOVE})
    @JoinColumn(name = "pfc")
    private Pfc pfc;

    @NotNull
    @Column(name = "nombre", nullable = false, length = MAX_LONGITUD_NOMBRE)
    private String nombre;

    @NotNull
    @Column(name= "apellidos", nullable = false, length = MAX_LONGITUD_APELLIDOS)
    private String apellidos;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "titulacion", nullable = false, length = TitulacionEnum.MAX_LONGITUD_TITULACION)
    private TitulacionEnum titulacion;

    @NotNull
    @Column(name = "domicilio", nullable = false, length = MAX_LONGITUD_DOMICILIO)
    private String domicilio;

    @NotNull
    @Column(name = "localidad", nullable = false, length = MAX_LONGITUD_LOCALIDAD)
    private String localidad;

    @NotNull
    @Column(name = "pais", nullable = false, length = MAX_LONGITUD_PAIS)
    private String pais;

    @NotNull
    @Column(name = "codigoPostal", nullable = false, length = MAX_LONGITUD_CODIGO_POSTAL)
    private String codigoPostal;

    @NotNull
    @Column(name = "telefono", nullable = false, length = MAX_LONGITUD_TELEFONO)
    private String telefono;

    @NotNull
    @Column(name = "email", nullable = false, length = MAX_LONGITUD_EMAIL)
    private String email;

    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern= DateFormatPatterns.yyyy_MM_dd, timezone="CET")
    @Column(name="fechaNacimiento", nullable = false)
    private Date fechaNacimiento;

    public Alumno() {}

    public Alumno(Alumno a) {
        this(a.getDni(), a.getNombre(), a.getApellidos(), a.getTitulacion(), a.getDomicilio(), a.getLocalidad(), a.getPais(), a.getCodigoPostal(), a.getTelefono(), a.getEmail(), a.getFechaNacimiento());
    }

    public Alumno(String dni, String nombre, String apellidos, TitulacionEnum titulacion, String domicilio, String localidad, String pais, String codigoPostal, String telefono, String email, Date fechaNacimiento) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.titulacion = titulacion;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.pais = pais;
        this.codigoPostal = codigoPostal;
        this.telefono = telefono;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
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

    public Pfc getPfc() {
        return pfc;
    }

    public void setPfc(Pfc pfc) {
        this.pfc = pfc;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


}

