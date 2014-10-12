package com.uma.informatica.persistence.models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by rafa on 07/07/14.
 */

/**
 * Generar metadatos.  MUY IMPORTANTE, estas clases deben estar en el mismo paquete donde se encuentran las entidades.
 */
@StaticMetamodel(Profesor.class)
public class Profesor_ {

    public static volatile SingularAttribute<Profesor, Long> id;
}





