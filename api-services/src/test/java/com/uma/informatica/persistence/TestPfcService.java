package com.uma.informatica.persistence;

import com.google.common.collect.Lists;
import com.uma.informatica.persistence.configuration.ServiceContext;
import com.uma.informatica.persistence.exceptions.AlumnoNoEncontradoException;
import com.uma.informatica.persistence.exceptions.InvalidPfcDataCreationException;
import com.uma.informatica.persistence.exceptions.PfcNoEncontradoException;
import com.uma.informatica.persistence.exceptions.ProfesorNoEncontradoException;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import com.uma.informatica.persistence.services.AlumnoService;
import com.uma.informatica.persistence.services.PfcService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**ª
 * Created by rafaordonez on 31/01/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceContext.class)
@Transactional
@ActiveProfiles("production")
@TransactionConfiguration
public class TestPfcService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    private PfcService pfcService;

    @Inject
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

    @Test(expected = InvalidPfcDataCreationException.class)
    public void testCreatePfcWithEmptyList() {
        List<Long> profesores = new ArrayList<>();
        this.pfcService.createPfc("nombre", "departamento", EstadoPfc.NO_EMPEZADO, profesores);
    }

    @Test(expected = InvalidPfcDataCreationException.class)
    public void testCreatePfcWithNullList() {
        this.pfcService.createPfc("nombre", "departamento", EstadoPfc.NO_EMPEZADO, null);
    }

    @Test
    public void testCreatePfc() {
        List<Long> profesores = new ArrayList<>();
        profesores.add(1L);
        profesores.add(2L);
        this.pfcService.createPfc("nombre", "departamento", EstadoPfc.NO_EMPEZADO, profesores);
    }

    @Test
    public void testCreatePfcWithNonValidDirector() {
        List<Long> profesores = new ArrayList<>();
        profesores.add(Long.MAX_VALUE);
        profesores.add(2L);
        this.pfcService.createPfc("nombre", "departamento", EstadoPfc.NO_EMPEZADO, profesores);
    }

    @Test
    public void testDeletePfc() {
        Pfc pfc = this.pfcService.deletePfc(1L);

        assertThat(pfc, not(nullValue()));
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testDeletePfcWithException() {
        Pfc pfc = this.pfcService.deletePfc(Long.MAX_VALUE);
        assertThat(pfc, nullValue());
    }

    @Test
    public void testUpdateNombre() {
        Pfc pfc = this.pfcService.updateNombre(1L, "Pepe");
        assertThat(pfc, notNullValue());
        assertThat(pfc.getNombre(), equalTo("Pepe"));
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testUpdateNombreWithException() {
        this.pfcService.updateNombre(Long.MAX_VALUE, null);
    }

    @Test
    public void testDeleteDirector() {
        List<Profesor> directorList = this.pfcService.deleteDirectors(1L, Lists.newArrayList(1L));
        assertThat(directorList, notNullValue());
        assertThat(directorList, not(anyOf(hasProperty("id", is(1L)))));
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testDeleteDirectorPfcNotFound() {
        this.pfcService.deleteDirectors(Long.MAX_VALUE, Lists.newArrayList(1L));
    }

    @Test(expected = ProfesorNoEncontradoException.class)
    public void testDeleteDirectorProfesorNotFound() {
        this.pfcService.deleteDirectors(1L, Lists.newArrayList(Long.MAX_VALUE));
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

    @Test
    public void testUpdateEstado() {
        Pfc pfc = this.pfcService.updateEstado(1L, EstadoPfc.FINALIZADO);
        assertThat(pfc, notNullValue());

        Pfc pfcUpdated = this.pfcService.findById(1L);
        assertThat(pfcUpdated.getEstado(), equalTo(pfc.getEstado()));
    }

    @Test(expected = PfcNoEncontradoException.class)
    public void testUpdateEstadoWithPfcNotFound() {
        this.pfcService.updateEstado(Long.MAX_VALUE, null);
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

    //    Collection<Profesor> getPfcDirectors(Long pfcId);
}
