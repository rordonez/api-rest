INSERT INTO profesor (dni, nombre, apellidos, titulacion, empresa, cargo, direccionEmpresa, telefono, email) VALUES ('32658329G', 'Pepe', 'Montoro Casas', 'SUPERIOR', '', '', '', '678356436', 'montoro@uma.es');
INSERT INTO profesor (dni, nombre, apellidos, titulacion, empresa, cargo, direccionEmpresa, telefono, email) VALUES ('34678346I', 'Raúl', 'Sanchez Mandado', 'GESTION', null, null, null, '678456382', 'raul@gmail.com');
INSERT INTO profesor (dni, nombre, apellidos, titulacion, empresa, cargo, direccionEmpresa, telefono, email) VALUES ('78943278U', 'Jaime', 'Hurtado Molina', 'SISTEMAS', '', '', '', '675234987', 'hmolina@uma.es');

INSERT INTO pfc (nombre, departamento, fechaInicio, estado, directorAcademico) VALUES ('Tecnologías de la Innovación', 'Lenguajes y Ciencias de la Computación', '2001-09-23', 'FINALIZADO', null);
INSERT INTO pfc (nombre, departamento, fechaInicio, estado, directorAcademico) VALUES ('Genoma Humano', 'Matemática aplicada', '2004-09-23', 'SIN_ASIGNAR', null);
INSERT INTO pfc (nombre, departamento, fechaInicio, estado, directorAcademico) VALUES ('Calculadora científica', 'Arquitectura de Computadores', '2013-07-29', 'PENDIENTE_ANTEPROYECTO', null);
INSERT INTO pfc (nombre, departamento, fechaInicio, estado, directorAcademico) VALUES ('Manejo de taxonomías financieras', 'Matemática aplicada', '2012-06-12', 'EMPEZADO', null);
INSERT INTO pfc (nombre, departamento, fechaInicio, estado, directorAcademico) VALUES ('Rest Api', 'Lenguajes y Ciencias de la Computación', '2013-07-01', 'EMPEZADO', null);


INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (1, 1);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (5, 1);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (2, 2);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (3, 2);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (4, 2);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (5, 2);

INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (1, 1);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (5, 1);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (2, 2);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (3, 2);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (4, 2);
INSERT INTO pfcs_profesores (pfc_id, profesor_id) VALUES (5, 2);