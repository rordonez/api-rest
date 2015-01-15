package com.uma.informatica.persistence;

import com.uma.informatica.persistence.configuration.ServiceContext;
import com.uma.informatica.persistence.exceptions.AlumnoNoEncontradoException;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.services.AlumnoService;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceContext.class)
@Transactional
@TransactionConfiguration
@ActiveProfiles("test")
public class TestAlumnoService {

    @Inject
    private AlumnoService alumnoService;

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

    @Test
    public void testSearchWithoutResults() {
        Collection<Alumno> alumnos = alumnoService.search("325353234A","asd","sdg");
        assertThat(alumnos, empty());
    }

    @Test
    public void testSearch() {
        Collection<Alumno> alumnos = alumnoService.search("2345E", "Rafa", "ea");
        assertThat(alumnos, not(empty()));
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

    @Test
    public void updateTest() {
        Alumno alumno = alumnoService.createAlumno("12345678A", "sdaf", "sadb aw", "SISTEMAS", "sdag", "sdgdsb", "sdag", "23532", "3463426", "asdg", new Date());
        Assert.assertNotNull(alumno);

        alumno = alumnoService.updateDireccion(alumno.getId(), "sdaf", "sadb aw", "sdag", "23532");
        Assert.assertNotNull(alumno);

        alumno = alumnoService.updateEmail(alumno.getId(), "sdaf");
        Assert.assertNotNull(alumno);

        alumno = alumnoService.updateTelefono(alumno.getId(), "111222333");
        Assert.assertNotNull(alumno);

        alumnoService.deleteAlumno(alumno.getId());
    }
}
