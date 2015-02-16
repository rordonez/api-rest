CREATE DATABASE  IF NOT EXISTS `restapi` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `restapi`;

-- MySQL dump 10.13  Distrib 5.6.15, for osx10.9 (x86_64)
--
-- Host: localhost    Database: restapi
-- ------------------------------------------------------
-- Server version 5.6.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


--
-- Table structure for table `profesor`
--

DROP TABLE IF EXISTS profesor;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE profesor (
  id         BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  dni        VARCHAR(9) NOT NULL UNIQUE,
  nombre     VARCHAR(45)  NOT NULL,
  apellidos  VARCHAR(100) NOT NULL,
  titulacion VARCHAR(80)  NOT NULL,
  empresa    VARCHAR(80) DEFAULT NULL,
  cargo      VARCHAR(60) DEFAULT NULL,
  direccionEmpresa VARCHAR(100) DEFAULT NULL,
  telefono   VARCHAR(12)  NOT NULL,
  email      VARCHAR(80)  NOT NULL
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;

/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `pfc`
--

DROP TABLE IF EXISTS pfc;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE pfc (
  id                   BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  nombre               VARCHAR(100) NOT NULL,
  departamento         VARCHAR(100) NOT NULL,
  fechaInicio          DATE DEFAULT NULL,
  estado               VARCHAR(45)  NOT NULL,
  directorAcademico BIGINT(20) DEFAULT NULL,
  CONSTRAINT FK_PFC_TO_PROFESOR FOREIGN KEY (directorAcademico) REFERENCES profesor (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pfcs_profesores`
--

DROP TABLE IF EXISTS pfcs_profesores;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE pfcs_profesores (
  pfc_id BIGINT(20) NOT NULL,
  profesor_id BIGINT(20) NOT NULL,
  PRIMARY KEY (pfc_id, profesor_id),
  CONSTRAINT FK_FROM_PFCSPROF_TO_PROF FOREIGN KEY (profesor_id) REFERENCES profesor (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT FK_FROM_PFCSPROF_TO_PFC FOREIGN KEY (pfc_id) REFERENCES pfc (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS alumno;

CREATE TABLE alumno
(
  id              BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  dni             VARCHAR(9) NOT NULL UNIQUE,
  pfc             BIGINT(20) DEFAULT NULL,
  nombre          VARCHAR(45)  NOT NULL,
  apellidos       VARCHAR(100) NOT NULL,
  titulacion      VARCHAR(80)  NOT NULL,
  domicilio       VARCHAR(100) NOT NULL,
  localidad       VARCHAR(60)  NOT NULL,
  pais            VARCHAR(45)  NOT NULL,
  codigoPostal    VARCHAR(5)   NOT NULL,
  telefono        VARCHAR(12)  NOT NULL,
  email           VARCHAR(80) DEFAULT NULL,
  fechaNacimiento DATE         NOT NULL,
  CONSTRAINT FK_TO_PFC FOREIGN KEY (pfc) REFERENCES pfc (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;