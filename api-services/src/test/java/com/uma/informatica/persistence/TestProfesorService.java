package com.uma.informatica.persistence;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.uma.informatica.persistence.configuration.ServiceContext;
import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import com.uma.informatica.persistence.services.ProfesorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by rafaordonez on 04/02/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@ContextConfiguration(classes = ServiceContext.class)
@ActiveProfiles("test")
@DatabaseSetup("/dataset.xml")
public class TestProfesorService {

    @Inject
    private ProfesorService profesorService;

    @Test(expected = NullPointerException.class)
    public void testWithNoResults() throws Exception {
        List<Profesor> profesores = this.profesorService.getPfcDirectors(1000L);
    }

    @Test
    public void testOneProfesor() throws Exception {
        List<Profesor> profesores = this.profesorService.getPfcDirectors(1L);
        assertThat(profesores.size(), is(1));
        assertThat(profesores.get(0), allOf(
                hasProperty("dni", is("32658329G")),
                hasProperty("nombre", is("Pepe")),
                hasProperty("apellidos", is("Montoro Casas"))
        ));
    }

    @Test
    public void testCreate() throws Exception {
        this.profesorService.createProfesor("asdf", "sdafg", "asdfg", TitulacionEnum.GESTION, "asdb", "asdg");
    }

    @Test
    public void testMultiple() throws Exception {
        List<Profesor> profesores = this.profesorService.getPfcDirectors(5L);
        assertThat(profesores.size(), is(2));
        assertThat(profesores, contains(
                allOf(
                        hasProperty("dni", is("32658329G")),
                        hasProperty("nombre", is("Pepe")),
                        hasProperty("apellidos", is("Montoro Casas"))),
                allOf(
                        hasProperty("dni", is("34678346I")),
                        hasProperty("nombre", is("Raúl")),
                        hasProperty("apellidos", is("Sanchez Mandado")))
        ));
    }
}
