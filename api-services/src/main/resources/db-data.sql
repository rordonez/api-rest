
INSERT INTO pfc (nombre, departamento, fechaInicio, estado, directorAcademico) VALUES ('Tecnologías de la Innovación', 'Lenguajes y Ciencias de la Computación', '2001-09-23', 'FINALIZADO', null);
INSERT INTO pfc (nombre, departamento, fechaInicio, estado, directorAcademico) VALUES ('Genoma Humano', 'Matemática aplicada', '2004-09-23', 'SIN_ASIGNAR', null);
INSERT INTO pfc (nombre, departamento, fechaInicio, estado, directorAcademico) VALUES ('Calculadora científica', 'Arquitectura de Computadores', '2013-07-29', 'PENDIENTE_ANTEPROYECTO', null);
INSERT INTO pfc (nombre, departamento, fechaInicio, estado, directorAcademico) VALUES ('Manejo de taxonomías financieras', 'Matemática aplicada', '2012-06-12', 'EMPEZADO', null);
INSERT INTO pfc (nombre, departamento, fechaInicio, estado, directorAcademico) VALUES ('Rest Api', 'Lenguajes y Ciencias de la Computación', '2013-07-01', 'EMPEZADO', null);

INSERT INTO profesor (dni, nombre, apellidos, titulacion, empresa, cargo, direccionEmpresa, telefono, email) VALUES ('32658329G', 'Pepe', 'Montoro Casas', 'ING SUPERIOR', '', '', '', '678356436', 'montoro@uma.es');
INSERT INTO profesor (dni, nombre, apellidos, titulacion, empresa, cargo, direccionEmpresa, telefono, email) VALUES ('34678346I', 'Raúl', 'Sanchez Mandado', 'Master en Comunicaciones', null, null, null, '678456382', 'raul@gmail.com');
INSERT INTO profesor (dni, nombre, apellidos, titulacion, empresa, cargo, direccionEmpresa, telefono, email) VALUES ('78943278U', 'Jaime', 'Hurtado Molina', 'ING SISTEMAS', '', '', '', '675234987', 'hmolina@uma.es');

INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (1, 1);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (5, 1);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (2, 2);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (3, 2);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (4, 2);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (5, 2);

INSERT INTO alumno (dni, pfc, nombre, apellidos, titulacion, domicilio, localidad, pais, codigoPostal, telefono, email, fechaNacimiento) VALUES ('00000000A', 5, 'Rafa', 'ssdag', 'SISTEMAS', 'sdagasg', 'sdagf', 'sdag', '12342', '325323', 'adsgadsg', '2014-01-29');
INSERT INTO alumno (dni, pfc, nombre, apellidos, titulacion, domicilio, localidad, pais, codigoPostal, telefono, email, fechaNacimiento) VALUES ('34638749J', 2, 'Silvia', 'Cantos Rodríguez', 'GESTION', 'Plaza de la Constitución, 25', 'Málaga', 'España', '35633', '678345789', 'E@e.es', '1978-02-04');
INSERT INTO alumno (dni, pfc, nombre, apellidos, titulacion, domicilio, localidad, pais, codigoPostal, telefono, email, fechaNacimiento) VALUES ('34789345O', 3, 'Alvaro', 'Pérez Rodríguez', 'SUPERIOR', 'Gran vía, 353', 'Madrid', 'España', '34789', '678343234', 'edt@fsgh.es', '1985-04-22');
INSERT INTO alumno (dni, pfc, nombre, apellidos, titulacion, domicilio, localidad, pais, codigoPostal, telefono, email, fechaNacimiento) VALUES ('36473824I', 4, 'Mónica', 'Gutierrez Castello', 'GESTION', 'Avenida de Manoteras, 34', 'Madrid', 'España', '34532', '678678678', 'esdg@df.com', '1988-08-14');
INSERT INTO alumno (dni, pfc, nombre, apellidos, titulacion, domicilio, localidad, pais, codigoPostal, telefono, email, fechaNacimiento) VALUES ('23423423U', 4, 'Iván', 'Ramirez Ramirez', 'SUPERIOR', 'Calle de Santalucía, S/N', 'Oviedo', 'España', '34532', '678980839', 'ewavg@sg.es', '1987-02-02');
INSERT INTO alumno (dni, pfc, nombre, apellidos, titulacion, domicilio, localidad, pais, codigoPostal, telefono, email, fechaNacimiento) VALUES ('73894276N', 1, 'Antonio', 'Pérez Martinez', 'SUPERIOR', 'Calle Benito Pérez Galdos, 2', 'Málaga', 'España', '34532', '648732345', 'example@e.com', '1980-01-21');
INSERT INTO alumno (dni, pfc, nombre, apellidos, titulacion, domicilio, localidad, pais, codigoPostal, telefono, email, fechaNacimiento) VALUES ('sadg', null, 'sdaf', 'sadb aw', 'SISTEMAS', 'sdag', 'sdgdsb', 'sdag', '23532', '3463426', 'asdg', '2014-01-29');