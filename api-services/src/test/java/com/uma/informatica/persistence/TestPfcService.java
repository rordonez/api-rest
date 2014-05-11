package com.uma.informatica.persistence;

import com.uma.informatica.persistence.configuration.ServiceConfiguration;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.services.PfcService;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**ª
 * Created by rafaordonez on 31/01/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@ActiveProfiles("production")
@TransactionConfiguration
public class TestPfcService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    private PfcService pfcService;


    @Test
    public void findPfcTest() {
        Pfc pfc = pfcService.findById(1L);
        assertThat(pfc, not(nullValue()));

        pfc = pfcService.findById(10L);
        assertThat(pfc, nullValue());
    }

    @Test
    public void findDepartamentoTest() {
        List<Pfc> pfcList = this.pfcService.findByDepartamento("Lenguajes y Ciencias de la Computación");
        assertThat(pfcList, not(nullValue()));
        assertThat(pfcList, not(empty()));

        pfcList = this.pfcService.findByDepartamento("sdagadsg");
        assertThat(pfcList, not(nullValue()));
        assertThat(pfcList, empty());
    }

    @Test
    public void findByNameTest() {
        Pfc pfc = this.pfcService.findByName("Genoma Humano");
        assertThat(pfc, not(nullValue()));


        pfc = this.pfcService.findByName("sadgdsag");
        assertThat(pfc, nullValue());
    }

    @Test
    public void testEstado() throws Exception {
        List<Pfc> pfcs = this.pfcService.findByEstado("EMPEZADO");
        assertThat(pfcs, not(empty()));
    }

    @Test
    public void testAddDirector() throws Exception {
        Pfc pfc = this.pfcService.findById(5L);
        assertThat(pfc, hasProperty("directores", hasSize(2)));

        this.pfcService.addDirector(5L, 3L);

        entityManager.flush();

        pfc = this.pfcService.findById(5L);
        assertThat(pfc, hasProperty("directores", hasSize(3)));
    }


//    Collection<Profesor> getPfcDirectors(Long pfcId);
}
