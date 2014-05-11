
CREATE TABLE if not exists profesor
(
  id BIGINT AUTO_INCREMENT,
  dni varchar(9),
  nombre varchar(45) NOT NULL,
  apellidos varchar(100) NOT NULL,
  titulacion varchar(80) NOT NULL,
  empresa varchar(80) DEFAULT NULL,
  cargo varchar(60) DEFAULT NULL,
  direccionEmpresa varchar(100) DEFAULT NULL,
  telefono varchar(12) NOT NULL,
  email varchar(80) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (dni)
);

CREATE TABLE if not exists pfc
(
  id BIGINT AUTO_INCREMENT,
  nombre varchar(100) NOT NULL,
  departamento varchar(100) NOT NULL,
  fechaInicio date DEFAULT NULL,
  estado varchar(45) NOT NULL,
  directorAcademico varchar(9),
  PRIMARY KEY (id),
  CONSTRAINT FK_DIRACADEMICO_TO_PROFESOR FOREIGN KEY (directorAcademico) REFERENCES profesor(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);


CREATE TABLE if not exists alumno
(
  id BIGINT AUTO_INCREMENT,
  dni varchar(9) UNIQUE,
  pfc bigint DEFAULT NULL,
  nombre varchar(45) NOT NULL,
  apellidos varchar(100) NOT NULL,
  titulacion varchar(80) NOT NULL,
  domicilio varchar(100) NOT NULL,
  localidad varchar(60) NOT NULL,
  pais varchar(45) NOT NULL,
  codigoPostal varchar(5) NOT NULL,
  telefono varchar(12) NOT NULL,
  email varchar(80) DEFAULT NULL,
  fechaNacimiento date NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_TO_PFC FOREIGN KEY (pfc) REFERENCES pfc (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);


CREATE TABLE if not exists pfcs_profesores
(
  pfc_id bigint NOT NULL,
  profesor_id varchar(9) NOT NULL DEFAULT '',
  PRIMARY KEY (pfc_id, profesor_id),
  CONSTRAINT FK_FROM_PFCPROF_TO_PROF FOREIGN KEY (profesor_id) REFERENCES profesor (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_FROM_PFCSPROF_TO_PFC FOREIGN KEY (pfc_id) REFERENCES pfc (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);