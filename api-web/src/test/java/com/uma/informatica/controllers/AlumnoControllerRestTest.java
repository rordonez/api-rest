package com.uma.informatica.controllers;

import com.uma.informatica.config.RestApiAppContext;
import com.uma.informatica.controllers.assemblers.AlumnoResourceAssembler;
import com.uma.informatica.controllers.assemblers.PfcResourceAssembler;
import com.uma.informatica.core.profiles.PropertyMockingApplicationContextInitializer;
import org.hamcrest.core.AnyOf;
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
import static org.springframework.http.HttpHeaders.ALLOW;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by rafa on 30/10/14.
 */

@ContextConfiguration(classes = {RestApiAppContext.class}, initializers = PropertyMockingApplicationContextInitializer.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AlumnoControllerRestTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                                        .dispatchOptions(true)
                                        .build();
    }

    @Test
    public void getAll_ShouldRender_200() throws Exception {
        mockMvc.perform(delete("/alumnos.json")
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.content", hasSize(10)))

                .andExpect(jsonPath("$.links", hasSize(4)))

                .andExpect(jsonPath("$.page.totalElements", is(13)))
                .andExpect(jsonPath("$.page.totalPages", is(2)))
                .andExpect(jsonPath("$.page.size", is(10)));
    }

    @Test
    public void alumnosOptions_ShouldRender204() throws Exception {
        mockMvc.perform(options("/alumnos"))

                .andExpect(status().isOk())
                .andExpect(header().string(ALLOW, AnyOf.<String>anyOf(containsString("GET"), containsString("PUT"), containsString("POST"), containsString("OPTIONS"))));
    }

    @Test
    public void getAll_WithPagination_ShouldRender_200() throws Exception {
        mockMvc.perform(get("/alumnos.json?page=1")
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.content", hasSize(3)))

                .andExpect(jsonPath("$.links", hasSize(4)))

                .andExpect(jsonPath("$.page.totalElements", is(13)));
    }





    @Test
    public void searchAlumnos_AlumnoNoEncontradoException_ShouldRender_404() throws Exception {
        mockMvc.perform(post("/alumnos.json?search=true")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockSearchAlumnosAlumnoNoEncontradoException()))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding( "UTF-8"))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún alumno")));
    }

    @Test
    public void searchAlumnos_ShouldRender_200() throws Exception {
        mockMvc.perform(post("/alumnos.json?search=true")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockSearchAlumnos()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.content", hasSize(10)))
                .andExpect(jsonPath("$.links", hasSize(4)))

                .andExpect(jsonPath("$.page.totalElements", is(12)));
    }

    @Test
    public void createAlumno_ShouldRender201View() throws Exception {

        mockMvc.perform(post("/alumnos.json")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockAlumnoJson()))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/alumnos/14")))

                .andExpect(jsonPath("$.dni", is("12345678A")))
                .andExpect(jsonPath("$.nombre", is("Nombre")))
                .andExpect(jsonPath("$.apellidos", is("Apellidos")))
                .andExpect(jsonPath("$.titulacion", is("SISTEMAS")))
                .andExpect(jsonPath("$.domicilio", is("Domicilio")))
                .andExpect(jsonPath("$.localidad", is("Localidad")))
                .andExpect(jsonPath("$.pais", is("Pais")))
                .andExpect(jsonPath("$.telefono", is("666666666")))
                .andExpect(jsonPath("$.codigoPostal", is("12345")))
                .andExpect(jsonPath("$.email", is("example@org.com")))
                .andExpect(jsonPath("$.fechaNacimiento", is("2014-01-29")));
    }


    @Test
    public void createAlumno_MethodArgumentNotValidException_ShouldRender400() throws Exception {

        mockMvc.perform(post("/alumnos.json")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .locale(new Locale("ES"))
                .content(mockAlumnoJsonWithDniAndNombreNull()))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void findById_AlumnoNoEncontradoException_ShouldRender404View() throws Exception {
    	mockMvc.perform(get("/alumnos/{alumnoId}.json", 0L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))
    			.andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
    			.andExpect(jsonPath("$[0].message", is("No se encontró ningún alumno con id: 0")));
    }

    @Test
    public void findById_ShouldRender200View() throws Exception {
        mockMvc.perform(get("/alumnos/{alumnoId}.json", 1L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.dni", hasToString("00000000A")))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/alumnos/1")))
                .andExpect(jsonPath("$.links[1].rel", is(AlumnoResourceAssembler.PFC_REL)))
                .andExpect(jsonPath("$.links[1].href", endsWith("/pfcs/5")));
    }

    @Test
    public void findById_WithoutPfc_ShouldRender200View() throws Exception {
        mockMvc.perform(get("/alumnos/{alumnoId}.json", 7L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.dni", is("11223344P")))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/alumnos/7")));
    }

    @Test
    public void updateAlumno_ShouldRender202() throws Exception {
        Long alumnoId = 1L;
        mockMvc.perform(patch("/alumnos/{alumnoId}.json", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockUpdateAlumnoJson()))

                .andExpect(status().isAccepted())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/alumnos/1")))
                .andExpect(jsonPath("$.links[1].rel", is(AlumnoResourceAssembler.PFC_REL)))
                .andExpect(jsonPath("$.links[1].href", endsWith("/pfcs/5")))

                .andExpect(jsonPath("$.domicilio", is("Domicilio actualizado")))
                .andExpect(jsonPath("$.localidad", is("Localidad actualizada")))
                .andExpect(jsonPath("$.pais", is("Pais actualizado")))
                .andExpect(jsonPath("$.codigoPostal", is("00000")))
                .andExpect(jsonPath("$.telefono", is("999999999")))
                .andExpect(jsonPath("$.email", is("actualizado@org.com")));
    }


    @Test
    public void updateAlumno_WithOnlyDirection_ShouldRender202() throws Exception {
        Long alumnoId = 1L;
        mockMvc.perform(patch("/alumnos/{alumnoId}.json", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockUpdateAlumnoWithOnlyDirectionJson()))

                .andExpect(status().isAccepted())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/alumnos/1")))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[1].href", endsWith("/pfcs/5")))
                .andExpect(jsonPath("$.links[1].rel", is(AlumnoResourceAssembler.PFC_REL)))

                .andExpect(jsonPath("$.domicilio", is("Domicilio actualizado")))
                .andExpect(jsonPath("$.localidad", is("Localidad actualizada")))
                .andExpect(jsonPath("$.pais", is("Pais actualizado")))
                .andExpect(jsonPath("$.codigoPostal", is("00000")))
                .andExpect(jsonPath("$.telefono", is("325323")))
                .andExpect(jsonPath("$.email", is("adsgadsg")));
    }

    @Test
    public void updateAlumno_WithOnlyEmail_ShouldRender202() throws Exception {
        Long alumnoId = 1L;
        mockMvc.perform(patch("/alumnos/{alumnoId}.json", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockUpdateAlumnoWithOnlyEmailJson()))

                .andExpect(status().isAccepted())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/alumnos/1")))
                .andExpect(jsonPath("$.links[1].rel", is(AlumnoResourceAssembler.PFC_REL)))
                .andExpect(jsonPath("$.links[1].href", endsWith("/pfcs/5")))

                .andExpect(jsonPath("$.domicilio", is("sdagasg")))
                .andExpect(jsonPath("$.localidad", is("sdagf")))
                .andExpect(jsonPath("$.pais", is("sdag")))
                .andExpect(jsonPath("$.codigoPostal", is("12342")))
                .andExpect(jsonPath("$.telefono", is("325323")))
                .andExpect(jsonPath("$.email", is("nuevoemail@org.com")));
    }

    @Test
    public void updateAlumno_AlumnoNoEncontradoException_ShouldRender404View() throws Exception {
        Long alumnoId = 0L;
        mockMvc.perform(patch("/alumnos/{alumnoId}.json", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .content("{}"))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$[" + alumnoId + "].message", is("No se encontró ningún alumno con id: 0")));
    }


    @Test
    public void deleteAlumno_AlumnoNoEncontradoException_ShouldRender404View() throws Exception {
        Long alumnoId = 0L;
        mockMvc.perform(delete("/alumnos/{alumnoId}.json", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[" + alumnoId + "].message", is("No se encontró ningún alumno con id: " + alumnoId)));
    }

    @Test
    public void deleteAlumno_ShouldRender202View() throws Exception {
        mockMvc.perform(delete("/alumnos/{alumnoId}.json", 1L)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isAccepted())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.dni", hasToString("00000000A")))
                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/alumnos/1")))
                .andExpect(jsonPath("$.links[1].rel", is(AlumnoResourceAssembler.PFC_REL)))
                .andExpect(jsonPath("$.links[1].href", endsWith("/pfcs/5")));
    }



    @Test
    public void addPfc_PfcNoEncontradoException_ShouldRender_404() throws Exception{
        Long alumnoId = 1L;
        Long pfcId = 10L;
        mockMvc.perform(put("/alumnos/{alumnoId}/pfc/{pfcId}.json", alumnoId, pfcId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún pfc con id: " + pfcId)))
                .andExpect(jsonPath("$[0].logref", is(pfcId.toString())))
                .andExpect(jsonPath("$[0].links", hasSize(0)));
    }

    @Test
    public void addPfc_ShouldRender200() throws Exception{
        Long alumnoId = 1L;
        Long pfcId = 1L;
        mockMvc.perform(put("/alumnos/{alumnoId}/pfc/{pfcId}.json", alumnoId, pfcId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/pfcs/1")))
                .andExpect(jsonPath("$.links[1].rel", is(PfcResourceAssembler.DIRECTORES_REL)))
                .andExpect(jsonPath("$.links[1].href", endsWith("/profesores?ids=1")));
    }



    @Test
    public void deletePfc_ShouldRender200() throws Exception{
        Long alumnoId = 1L;
        mockMvc.perform(delete("/alumnos/{alumnoId}/pfc.json", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/pfcs/5")))
                .andExpect(jsonPath("$.links[1].rel", is(PfcResourceAssembler.DIRECTORES_REL)))
                .andExpect(jsonPath("$.links[1].href", endsWith("/profesores?ids=1&ids=2")))

                .andExpect(jsonPath("$.directorAcademico", is(nullValue())));
    }


    @Test
    public void deletePfc_AlumnoNoEncontradoException_ShouldRender_404() throws Exception{
        Long alumnoId = Long.MAX_VALUE;
        mockMvc.perform(delete("/alumnos/{alumnoId}/pfc.json", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún alumno con id: " + alumnoId)))
                .andExpect(jsonPath("$[0].logref", is(alumnoId.toString())))
                .andExpect(jsonPath("$[0].links", hasSize(0)));
    }


    @Test
    public void deletePfc_AlumnoSinPfcException_ShouldRender_404() throws Exception {
        Long alumnoId = 7L;
        mockMvc.perform(delete("/alumnos/{alumnoId}/pfc.json", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Alumno: " + alumnoId +" no tiene Pfc asignado")))
                .andExpect(jsonPath("$[0].logref", is(alumnoId.toString())))
                .andExpect(jsonPath("$[0].links", hasSize(0)));
    }


    private String mockAlumnoJson() {
        return "{" +
                "            \"dni\": \"12345678A\"," +
                "            \"pfc\": null," +
                "            \"nombre\": \"Nombre\"," +
                "            \"apellidos\": \"Apellidos\"," +
                "            \"titulacion\": \"SISTEMAS\"," +
                "            \"domicilio\": \"Domicilio\"," +
                "            \"localidad\": \"Localidad\"," +
                "            \"pais\": \"Pais\"," +
                "            \"codigoPostal\": \"12345\"," +
                "            \"telefono\": \"666666666\"," +
                "            \"email\": \"example@org.com\"," +
                "            \"fechaNacimiento\": \"2014-01-29\"}";
    }

    private String mockAlumnoJsonWithDniAndNombreNull() {
        return "{" +
                "            \"dni\": null," +
                "            \"pfc\": null," +
                "            \"nombre\": null," +
                "            \"apellidos\": \"Apellidos\"," +
                "            \"titulacion\": \"SISTEMAS\"," +
                "            \"domicilio\": \"Domicilio\"," +
                "            \"localidad\": \"Localidad\"," +
                "            \"pais\": \"Pais\"," +
                "            \"codigoPostal\": \"12345\"," +
                "            \"telefono\": \"666666666\"," +
                "            \"email\": \"example@org.com\"," +
                "            \"fechaNacimiento\": \"2014-01-29\"}";
    }

    private String mockUpdateAlumnoJson() {
        return "{" +
                "      \"domicilio\": \"Domicilio actualizado\"," +
                "      \"localidad\": \"Localidad actualizada\"," +
                "      \"pais\": \"Pais actualizado\"," +
                "      \"codigoPostal\": \"00000\"," +
                "      \"telefono\": \"999999999\"," +
                "      \"email\": \"actualizado@org.com\"}";
    }

    private String mockUpdateAlumnoWithOnlyDirectionJson() {
        return "{" +
                "      \"domicilio\": \"Domicilio actualizado\"," +
                "      \"localidad\": \"Localidad actualizada\"," +
                "      \"pais\": \"Pais actualizado\"," +
                "      \"codigoPostal\": \"00000\"}";
    }

    private String mockUpdateAlumnoWithOnlyEmailJson() {
        return "{" +
                "            \"email\": \"nuevoemail@org.com\"}";
    }

    private String mockSearchAlumnosAlumnoNoEncontradoException() {
        return "{" +
                "            \"dni\": \"12345678A\"," +
                "            \"nombre\": \"Nombre\"," +
                "            \"apellidos\": \"Apellidos\"}";
    }

    private String mockSearchAlumnos() {
        return "{" +
                "            \"dni\": \"3\"," +
                "            \"nombre\": \"Nombre\"," +
                "            \"apellidos\": \"Apellidos\"}";
    }
}
