package com.uma.informatica.controllers;

import com.uma.informatica.config.RestApiAppContext;
import com.uma.informatica.core.profiles.PropertyMockingApplicationContextInitializer;
import com.uma.informatica.persistence.models.enums.EstadoPfc;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by rafa on 01/12/14.
 */
@ContextConfiguration(classes = {RestApiAppContext.class}, initializers = PropertyMockingApplicationContextInitializer.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class PfcControllerRestTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void getAll_ShouldRender200() throws Exception {
        mockMvc.perform(get("/pfcs.json")
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/pfcs{?page,size,sort}")));
    }

    @Test
    public void searchAlumnos_ShouldRender_200() throws Exception {
        mockMvc.perform(get("/pfcs.json?estado=EMPEZADO&search=true")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.links", hasSize(1)));
    }

    @Test
    public void searchAlumnos_PfcNoEncontradoException_ShouldRender_404() throws Exception {
        mockMvc.perform(get("/pfcs.json?search=true")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())

                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún pfc")));

    }

    @Test
    public void createPfc_ShouldRender201View() throws Exception {

        mockMvc.perform(post("/pfcs.json")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockPfcJson()))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/pfcs/6")))

                .andExpect(jsonPath("$.estado", is(EstadoPfc.NO_EMPEZADO.name())));
    }


    @Test
    public void invalidJsonMessage_HttpMessageNotReadableException_ShouldRender400() throws Exception {
        mockMvc.perform(post("/pfcs.json")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .locale(new Locale("ES"))
                .content("{+}"))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", containsString("Could not read JSON: Unexpected character")));
    }

    @Test
    public void createPfc_MethodArgumentNotValidException_ShouldRender400() throws Exception {

        mockMvc.perform(post("/pfcs.json")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .locale(new Locale("ES"))
                .content(mockPfcJsonWithoutDepartamento()))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Parámetro: departamento no puede ser null")));
    }

    @Test
    public void getPfc_ShouldRender200() throws Exception {
        mockMvc.perform(get("/pfcs/{pfcId}.json", 1L)
            .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/pfcs/1")))

                .andExpect(jsonPath("$.directores", hasSize(1)))
                .andExpect(jsonPath("$.directores[0].links", hasSize(1)))
                .andExpect(jsonPath("$.directores[0].links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.directores[0].links[0].href", endsWith("/profesores/1")));
    }

    @Test
    public void getPfc_ShouldRender404() throws Exception {
        mockMvc.perform(get("/pfcs/{pfcId}.json", 0L)
        .accept(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].message", is("No se encontró ningún pfc con id: 0")));
    }

    @Test
    public void removePfc_ShouldRender202() throws Exception {
        mockMvc.perform(delete("/pfcs/{pfcId}.json", 1L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isAccepted())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/pfcs/1")));
    }

    @Test
    public void removePfc_ShouldRender404() throws Exception {
        mockMvc.perform(delete("/pfcs/{pfcId}.json", 0L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún pfc con id: 0")));
    }

    @Test
    public void updateAlumno_ShouldRender202() throws Exception {
        mockMvc.perform(patch("/pfcs/{pfcId}.json", 1L)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockUpdatePfcJson()))

                .andExpect(status().isAccepted())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/pfcs/1")));
    }

    @Test
    public void updateAlumno_With_Invalid_ID_ShouldRender404() throws Exception {
        mockMvc.perform(patch("/pfcs/{pfcId}.json", 0L)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún pfc con id: 0")));
    }


    @Test
    public void updateDirectorAcademico_ShouldRender202() throws Exception {
        mockMvc.perform(put("/pfcs/{pfcId}/directoracademico/{profesorId}.json", 1L, 3L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isAccepted())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/profesores/3")));

    }

    @Test
    public void updateDirectorAcademico_WhenPfcIsNotFound_ShouldRender404() throws Exception {
        mockMvc.perform(put("/pfcs/{pfcId}/directoracademico/{profesorId}.json", 0L, 3L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún pfc con id: 0")));
    }

    @Test
    public void updateDirectorAcademico_WhenProfesorIsNotFound_ShouldRender404() throws Exception {
        mockMvc.perform(put("/pfcs/{pfcId}/directoracademico/{profesorId}.json", 1L, 0L))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Profesor con identificador: 0 no encontrado")));
    }

    @Test
    public void deleteDirectorAcademico_ShouldRender202() throws Exception {
        mockMvc.perform(delete("/pfcs/{pfcId}/directoracademico.json", 2L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isAccepted())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/profesores/2")));
    }

    @Test
    public void deleteDirectorAcademico_WithoutData() throws Exception {
            mockMvc.perform(delete("/pfcs/{pfcId}/directoracademico.json", 1L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))

                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].message", is("Profesor no encontrado")));
    }

    @Test
    public void testAddDirectores_ShouldRender202() throws Exception {
        mockMvc.perform(put("/pfcs/{pfcId}/directores.json?directores=1,2", 1)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].links[0].href", endsWith("/profesores/1")))
                .andExpect(jsonPath("$.content[1].links[0].href", endsWith("/profesores/2")));
    }

    @Test
    public void testAddDirectores_WithoutPfc_ShouldRender404() throws Exception {
        mockMvc.perform(put("/pfcs/{pfcId}/directores.json?directores=1,2", 0L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún pfc con id: 0")));

    }
    @Test
    public void testAddDirectores_InvalidDirectors_ShouldRender404() throws Exception {
        mockMvc.perform(put("/pfcs/{pfcId}/directores.json?directores=0", 0L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún pfc con id: 0")));

    }

    @Test
    public void testAddDirectores_InvalidDirectors_ShouldRender400() throws Exception {
        mockMvc.perform(put("/pfcs/{pfcId}/directores.json?directores", 1L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Required List parameter 'directores' is not present")));

    }

    @Test
    public void testDeleteDirectores_ShouldRender200() throws Exception {
        mockMvc.perform(delete("/pfcs/{pfcId}/directores.json", 1L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isAccepted())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].links[0].href", endsWith("/profesores/1")));
    }

    @Test
    public void testDeleteDirectores_WithoutPfc_ShouldRender404() throws Exception {
        mockMvc.perform(delete("/pfcs/{pfcId}/directores.json", 1L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isAccepted())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.links", hasSize(0)))
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testDeleteDirectores_WithoutPfc_Shouldrender404() throws Exception {
        mockMvc.perform(delete("/pfcs/{pfcId}/directores.json", 0L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún pfc con id: 0")));

    }

    @Test
    public void testDeleteDirectores_WithoutDirectores_Shouldrender404() throws Exception {
        mockMvc.perform(delete("/pfcs/{pfcId}/directores.json", 2L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Pfc con id: 2 no tiene directores asignados")));

    }

    private String mockUpdatePfcJson() {
        return "{" +
                "\"nombre\":\"Nombre actualizado\"," +
                "\"departamento\":\"Departamento actualizado\"," +
                "\"fechaInicio\":\"2015-03-11\"," +
                "\"estado\":\"EMPEZADO\"}";

    }

    private String mockPfcJson() {
        return "{" +
                "\"nombre\":\"Tecnologías de la Innovación\"," +
                "\"departamento\":\"Lenguajes y Ciencias de la Computacion\"}";
    }


    private String mockPfcJsonWithoutDepartamento() {

        return "{" +
                "\"nombre\":\"Tecnologías de la Innovación\"}";
    }


}
