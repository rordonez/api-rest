package com.uma.informatica.persistence.services;

import com.google.common.collect.Lists;
import com.uma.informatica.core.exceptions.*;
import com.uma.informatica.core.profiles.PropertyMockingApplicationContextInitializer;
import com.uma.informatica.persistence.configuration.ServiceContext;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**ª
 * Created by rafaordonez on 31/01/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceContext.class, initializers = PropertyMockingApplicationContextInitializer.class)
@Transactional
@TransactionConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class PfcServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PfcService pfcService;

    @Autowired
    private AlumnoService alumnoService;


    @Test
    public void findDepartamentoTest() {
        List<Pfc> pfcList = this.pfcService.findByDepartamento("Lenguajes y Ciencias de la Computación");
        assertThat(pfcList, not(nullValue()));
        assertThat(pfcList, not(empty()));

        pfcList = this.pfcService.findByDepartamento("sdagadsg");
        assertThat(pfcList, not(nullValue()));
        assertThat(pfcList, empty());

        pfcList = this.pfcService.findByDepartamento(null);
        assertThat(pfcList, not(nullValue()));
        assertThat(pfcList, empty());
    }

    @Test
    public void findByIdTest() {
        Pfc pfc = pfcService.findById(1L);
        assertThat(pfc, allOf(
                not(nullValue()),
                hasProperty("nombre", is(not(nullValue()))),
                hasProperty("departamento", is(not(nullValue()))),
                hasProperty("estado", is(not(nullValue()))),
                hasProperty("directores", is(not(empty())))
                ));


    }

    @Test
    public void findByPfcTest() {
        Collection<Alumno> alumnos = pfcService.findByPfc(1L);
        assertThat(alumnos, not(empty()));
        assertThat(alumnos, hasSize(1));

    }

    @Test(expected = PfcNoEncontradoException.class)
    public void findByIdTestWithException() {
        this.pfcService.findById(Long.MAX_VALUE);
    }

    @Test
    public void testGetAll() {
        List<Pfc> pfcs = this.pfcService.getAll();
        assertThat(pfcs, not(nullValue()));
        assertThat(pfcs, not(empty()));
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testGetAll_WithoutResults() throws Exception {
        //Given
        Page<Alumno> alumnos = this.alumnoService.getAll(new PageRequest(0, 10));
        for(Alumno alumno : alumnos) {
            alumno.setPfc(null);
            this.alumnoService.deletePfc(alumno.getId());
        }
        this.pfcService.deleteAll();

        //When
        this.pfcService.getAll();

        //Then throw an exception
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testSearchWithNullParameters() {
        this.pfcService.search(null, null, null);
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testSearchWithoutResults() {
        this.pfcService.search("Departamento", "Nombre", "ESTADO");
    }

    @Test
    public void testSearch() {
        List<Pfc> pfcs = this.pfcService.search("a", "a", "E");
        assertThat(pfcs, notNullValue());
        assertThat(pfcs, hasSize(5));
    }

    @Test
    public void findByNameTest() {
        List<Pfc> pfcs = this.pfcService.findByName("Genoma Humano");
        assertThat(pfcs, not(nullValue()));


        pfcs = this.pfcService.findByName("sadgdsag");
        assertThat(pfcs, empty());
    }

    @Test
    public void testEstado() throws Exception {
        List<Pfc> pfcs = this.pfcService.findByEstado(EstadoPfc.EMPEZADO);
        assertThat(pfcs, not(empty()));

    }

    @Test
    public void testCreatePfc() {
        this.pfcService.createPfc("nombre", "departamento");
    }

    @Test
    public void testDeletePfc() {
        //Given
        final Long DELETED_ID = 1L;
        Pfc pfc = this.pfcService. findById(DELETED_ID);
        List<Profesor> directores = pfc.getDirectores();

        //When
        Pfc deletedPfc = this.pfcService.deletePfc(DELETED_ID);

        //Then
        assertThat(deletedPfc.getDirectores(), is(nullValue()));
        assertFalse(this.pfcService.getAll().contains(pfc));

        //TODO Extract to a new operation at the same interface
        for(Profesor p : directores) {
            assertFalse(p.getPfcs().contains(deletedPfc));
        }

        //TODO Extract to a new operation at the same interface
        Page<Alumno> alumnos = this.alumnoService.getAll(new PageRequest(0, 10));
        for(Alumno a : alumnos) {
            if(null != a.getPfc()) {
                assertNotEquals(a.getPfc().getId(), DELETED_ID);
            }
        }
    }


    @Test(expected = PfcNoEncontradoException.class)
    public void testDeletePfcWithException() {
        Pfc pfc = this.pfcService.deletePfc(Long.MAX_VALUE);
        assertThat(pfc, nullValue());
    }

    @Test
    public void testUpdatePfc() {
        Pfc updatedPfc = this.pfcService.updatePfc(1L, "Pepe", "Nuevo Dep", new Date(), null, EstadoPfc.EMPEZADO);
        assertThat(updatedPfc, notNullValue());
        assertThat(updatedPfc.getNombre(), equalTo("Pepe"));
        assertThat(updatedPfc.getDepartamento(), equalTo("Nuevo Dep"));
        assertThat(updatedPfc.getFechaInicio(), is(notNullValue()));
        assertThat(updatedPfc.getFechaFin(), nullValue());
        assertThat(updatedPfc.getEstado(), is(EstadoPfc.EMPEZADO));
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testUpdatePfcWithException() {
        this.pfcService.updatePfc(Long.MAX_VALUE, null, null, null, null, null);
    }

    @Test
    public void testDeleteDirectors() {
        List<Profesor> directorList = this.pfcService.deleteDirectors(1L);
        assertThat(directorList, notNullValue());
        assertThat(directorList, not(anyOf(hasProperty("id", is(1L)))));
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testDeleteDirectorPfcNotFound() {
        this.pfcService.deleteDirectors(Long.MAX_VALUE);
    }

    @Test(expected = PfcSinDirectoresException.class)
    public void testDeleteDirectorProfesorNotFound() {
        this.pfcService.deleteDirectors(2L);
    }

    @Test
    public void testAddDirector() throws Exception {
        Pfc pfc = this.pfcService.findById(5L);
        assertThat(pfc, hasProperty("directores", hasSize(2)));

        this.pfcService.addDirectors(5L, Lists.newArrayList(3L));

        entityManager.flush();

        pfc = this.pfcService.findById(5L);
        assertThat(pfc, hasProperty("directores", hasSize(1)));
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testAddDirectorPfcNoEncontrado() {
        this.pfcService.addDirectors(Long.MAX_VALUE, Lists.newArrayList(1L));
    }

    @Test(expected = ProfesorNoEncontradoException.class)
    public void testAddDirectorProfesorNoEncontrado() {
        this.pfcService.addDirectors(5L, Lists.newArrayList(Long.MAX_VALUE));
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testChangeDirectorAcademicoWithoutPfc() {
        this.pfcService.changeDirectorAcademico(Long.MAX_VALUE, 1L);
    }

    @Test(expected = ProfesorNoEncontradoException.class)
    public void testChangeDirectorAcademicoWithoutDirector() {
        this.pfcService.changeDirectorAcademico(1L, Long.MAX_VALUE);
    }

    @Test
    public void testChangeDirectorAcademico() {
        Profesor profesor = this.pfcService.changeDirectorAcademico(5L, 1L);
        Assert.assertThat(profesor, notNullValue());
        Assert.assertThat(profesor, notNullValue());
        Assert.assertThat(1L, equalTo(profesor.getId()));
    }

    @Test(expected = AlumnoNoEncontradoException.class)
    public void testAddPfcWithoutAlumno() {
        this.pfcService.addPfcToAlumno(Long.MAX_VALUE, Long.MAX_VALUE);
    }

    @Test
    public void testAddPfc() {
        Pfc pfc = this.pfcService.addPfcToAlumno(1L, 1L);
        Alumno newAlumno = this.alumnoService.findById(1L);
        Assert.assertThat(pfc, notNullValue());
        Assert.assertThat(newAlumno, notNullValue());
        Assert.assertThat(newAlumno.getPfc().getId().longValue(), equalTo(pfc.getId().longValue()));
        Pfc newPfc = this.pfcService.findById(pfc.getId());
        Assert.assertThat(newPfc, notNullValue());
        Assert.assertThat(newPfc.getId(), equalTo(pfc.getId().longValue()));
    }

    @Test
    public void testDeletePfcFromAlumno() {
        Alumno alumno = alumnoService.findById(3L);
        Pfc pfc = this.pfcService.deletePfcFromAlumno(alumno.getId());

        assertThat(pfc, not(nullValue()));
        alumno = this.alumnoService.findById(3L);
        Assert.assertThat(alumno.getPfc(), nullValue());
    }

    @Test(expected = AlumnoNoEncontradoException.class)
    public void testDeletePfcFromNoAlumno() throws Exception {
        this.pfcService.deletePfcFromAlumno(0L);
    }

    @Test(expected = AlumnoSinPfcException.class)
    public void testDeletePfcAlumnoWithoutPfc() throws Exception {
        this.pfcService.deletePfcFromAlumno(7L);

    }

    @Test
    public void testGetDirectorAcademico() throws Exception {
        Profesor profesor = this.pfcService.findByDirectorAcademico(2L);

        assertThat(profesor, is(notNullValue()));
        assertThat(profesor.getId(), is(2L));
    }

    @Test(expected = DirectorAcademicoNotFoundException.class)
    public void testGetDirectorAcademico_WithoutResults() throws Exception {
        Profesor profesor = this.pfcService.findByDirectorAcademico(1L);
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testGetDirectorAcademico_PfcNotFound() throws Exception {
        Profesor profesor = this.pfcService.findByDirectorAcademico(0L);
    }

    @Test
    public void testDeleteDirectorAcademico() throws Exception {
        //Given
        Pfc pfc = this.pfcService.findById(2L);
        Long id = pfc.getDirectorAcademico().getId();

        //When
        Profesor profesor = this.pfcService.deleteDirectorAcademico(2L);

        //Then
        assertThat(profesor, is(notNullValue()));
        assertEquals(profesor.getId(), id);
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testDeleteDirectorAcademico_WhenPfcIsNotFound() throws Exception {
        //Given

        //When
        this.pfcService.deleteDirectorAcademico(0L);
    }

    @Test(expected = ProfesorNoEncontradoException.class)
    public void testDeleteDirectorAcademico_WhenThereIsNoDirectorAcademico() throws Exception {
        //Given
        Pfc pfd = this.pfcService.findById(1L);

        //When
        this.pfcService.deleteDirectorAcademico(1L);
    }

}
