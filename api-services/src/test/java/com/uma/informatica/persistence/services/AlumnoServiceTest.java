package com.uma.informatica.persistence.services;

import com.uma.informatica.core.exceptions.AlumnoNoEncontradoException;
import com.uma.informatica.core.profiles.PropertyMockingApplicationContextInitializer;
import com.uma.informatica.persistence.configuration.ServiceContext;
import com.uma.informatica.persistence.models.Alumno;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Collection;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by rafaordonez on 27/01/14.
 *
 * Esta clase realiza los test del servicio
 * {@link com.uma.informatica.persistence.services.AlumnoService AlumnoService} utilizando un base de datos H2
 * empotrada.
 *
 * Para comprobar que la conexión a la base de datos es correcta creamos una bean
 * {@link org.springframework.jdbc.core.JdbcTemplate JdbcTemplate} pasándole el Datasource utilizado por la base de
 * datos empotrada. Necesitamos importar la clase
 * {@link com.uma.informatica.persistence.configuration.EmbeddedDataSourceConfiguration EmbeddedDataSourceConfiguration}
 * ya que es la que contiene la configuración al bean {@link javax.sql.DataSource DataSource}
 *
 */

@ContextConfiguration(classes = ServiceContext.class, initializers = PropertyMockingApplicationContextInitializer.class)
@ActiveProfiles("test")
public class AlumnoServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AlumnoService alumnoService;

    @Test
    public void testGetAll() throws Exception {
        //Given

        //When
        Page<Alumno> alumnos = this.alumnoService.getAll(new PageRequest(0, 10));

        //Then
        assertThat(alumnos, is(notNullValue()));
        assertThat(alumnos.getTotalPages(), is(2));
        assertEquals(alumnos.getTotalElements(), 13L);
    }

    @Test(expected = AlumnoNoEncontradoException.class)
    public void testGetAll_WithoutResults() throws Exception {
        //Given
        this.alumnoService.deleteAll();

        //When
        this.alumnoService.getAll(new PageRequest(0, 10));

        //Then exception is thrown
    }

    @Test
    public void testFindById()  {
        Alumno alumno = this.alumnoService.findById(1L);
        assertThat(alumno, allOf(
                not(nullValue()),
                hasProperty("pfc", is(not(nullValue()))),
                hasProperty("pfc", allOf(
                        hasProperty("id", is(not(nullValue()))),
                        hasProperty("nombre", is(not(nullValue()))),
                        hasProperty("departamento", is(not(nullValue()))),
                        hasProperty("estado", is(not(nullValue()))),
                        hasProperty("fechaInicio", is(not(nullValue()))),
                        hasProperty("directores", is(not(empty())))
                ))));
    }

    @Test(expected = AlumnoNoEncontradoException.class)
    public void testFindByIdWithException() {
        this.alumnoService.findById(Long.MAX_VALUE);
    }


    @Test
    public void findByDniTest() {
        Alumno alumno = alumnoService.findByDni("00000000A");
        assertThat(alumno, not(nullValue()));
        alumno = alumnoService.findByDni(RandomStringUtils.random(8));
        assertThat(alumno, nullValue());
    }

    @Test(expected = AlumnoNoEncontradoException.class)
    public void testSearchWithoutResults() {
        Page<Alumno> alumnos = alumnoService.search("325353234A","asd","sdg", new PageRequest(0,10));
        assertEquals(alumnos.getTotalElements(), is(0));
    }

    @Test
    public void testSearch() {
        Page<Alumno> alumnos = alumnoService.search("2345E", "Rafa", "ea", new PageRequest(0, 10));
        assertEquals(alumnos.getTotalElements(), 1L);
    }

    @Test
    public void findAlumnoByNombreTest() {
        Collection<Alumno> alumno = alumnoService.findByNombre("Rafa");
        Assert.assertFalse(alumno.isEmpty());

        alumno = alumnoService.findByNombre(RandomStringUtils.randomAlphabetic(13));
        Assert.assertTrue(alumno.isEmpty());
    }

    @Test
    public void findByNombreYApellidos() {
        Collection<Alumno> alumnos = alumnoService.findByNombreYApellidos("Rafa", "Ordóñez Vivar");
        Assert.assertFalse(alumnos.isEmpty());

        alumnos = alumnoService.findByNombreYApellidos(RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphabetic(24));
        Assert.assertTrue(alumnos.isEmpty());
    }

    @Test
    public void createTest() {
        Alumno alumno = alumnoService.createAlumno("12345678A", "sdaf", "sadb aw", "SISTEMAS", "sdag", "sdgdsb", "sdag", "23532", "3463426", "asdg", new Date());
        Assert.assertNotNull(alumno);

        assertThat(alumno,  equalTo(alumnoService.findByDni("12345678A")));
    }

    @Test
    public void removeTest() {
        Alumno alumno = alumnoService.findByDni("12345678A");
        if (alumno != null) {
            alumnoService.deleteAlumno(alumno.getId());
        }
        alumno = alumnoService.findByDni("12345678A");
        assertThat(alumno, nullValue());

    }

    @Test(expected = AlumnoNoEncontradoException.class)
    public void testDeleteAlumno_WithoutResults() throws Exception {
        //Given
        this.alumnoService.deleteAll();

        //When
        this.alumnoService.deleteAlumno(RandomUtils.nextLong(0, 100));

        //Then exception is thrown
    }

    @Test(expected = AlumnoNoEncontradoException.class)
    public void testUpdateAlumno_WithoutResults() throws Exception {
        //Given
        this.alumnoService.deleteAll();

        //When
        this.alumnoService.updateAlumno(RandomUtils.nextLong(0, 100), null, null, null, null, null, null);

        //Then exception is thrown
    }

    @Test
    public void updateTest() {
        Alumno alumno = alumnoService.createAlumno("12345678A", "sdaf", "sadb aw", "SISTEMAS", "sdag", "sdgdsb", "sdag", "23532", "3463426", "asdg", new Date());
        Assert.assertNotNull(alumno);

        alumno = alumnoService.updateAlumno(alumno.getId(), "sdaf", "sadb aw", "sdag", "23532", "sdaf", "1122334455");
        Assert.assertNotNull(alumno);


        alumnoService.deleteAlumno(alumno.getId());
    }
}
