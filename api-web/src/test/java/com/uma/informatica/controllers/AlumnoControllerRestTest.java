package com.uma.informatica.controllers;

import com.uma.informatica.config.RestApiAppContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by rafa on 30/10/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestApiAppContext.class})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ActiveProfiles("test")
public class AlumnoControllerRestTest {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new StringHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());

        this.restTemplate = new RestTemplate();
        this.restTemplate.setMessageConverters(converters);

        this.mockServer = MockRestServiceServer.createServer(this.restTemplate);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getAll_ShouldRender_200() throws Exception {
        mockMvc.perform(get("/alumnos")
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.content", hasSize(7)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[2].id", is(3)))
                .andExpect(jsonPath("$.content[3].id", is(4)))
                .andExpect(jsonPath("$.content[4].id", is(5)))
                .andExpect(jsonPath("$.content[5].id", is(6)))
                .andExpect(jsonPath("$.content[6].id", is(7)));
    }




    @Test
    public void searchAlumnos_AlumnoNoEncontradoException_ShouldRender_404() throws Exception {
        mockMvc.perform(post("/alumnos")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockSearchAlumnosAlumnoNoEncontradoException()))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún alumno")));
    }

    @Test
    public void searchAlumnos_ShouldRender_200() throws Exception {
        mockMvc.perform(post("/alumnos")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockSearchAlumnos()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.links", hasSize(0)))
                .andExpect(jsonPath("$.content[0].id", is(2)))
                .andExpect(jsonPath("$.content[1].id", is(3)))
                .andExpect(jsonPath("$.content[2].id", is(4)))
                .andExpect(jsonPath("$.content[3].id", is(5)))
                .andExpect(jsonPath("$.content[4].id", is(6)));
    }

    @Test
    public void createAlumno_ShouldRender201View() throws Exception {

        mockMvc.perform(put("/alumnos")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockAlumnoJson()))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/alumnos/8")))
                .andExpect(jsonPath("$.id", is(8)))
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

        mockMvc.perform(put("/alumnos")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .locale(new Locale("ES"))
                .content(mockAlumnoJsonWithDniAndNombreNull()))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(jsonPath("$.links", hasSize(0)))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

//    @Test
//    public void delete_HttpRequestMethodNotSupportedException_ShoudRender405() throws Exception {
//        mockMvc.perform(delete("/alumnos")
//                .accept(IntegrationTestUtil.applicationJsonMediaType)
//                .contentType(IntegrationTestUtil.applicationJsonMediaType)
//                .content("{}"))
//
//                .andExpect(status().isMethodNotAllowed())
//                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
//                .andExpect(jsonPath("$.links", hasSize(0)))
//                .andExpect(jsonPath("$.content", hasSize(1)))
//                .andExpect(jsonPath("$.content[0].message", is("Method not supported")));
//
//    }

    @Test
    public void findById_AlumnoNoEncontradoException_ShouldRender404View() throws Exception {
    	mockMvc.perform(get("/alumnos/{alumnoId}", 0L)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))
    			.andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
    			.andExpect(jsonPath("$[0].message", is("No se encontró ningún alumno con id: 0")));
    }

    @Test
    public void findById_ShouldRender200View() throws Exception {
        mockMvc.perform(get("/alumnos/{alumnoId}", 1L)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.dni", hasToString("00000000A")))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/alumnos/1")))
                .andExpect(jsonPath("$.links[1].rel", is("pfc")))
                .andExpect(jsonPath("$.links[1].href", is("http://localhost/pfcs/5")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void findById_WithoutPfc_ShouldRender200View() throws Exception {
        mockMvc.perform(get("/alumnos/{alumnoId}", 7L)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.dni", hasToString("sadg")))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/alumnos/7")))
                .andExpect(jsonPath("$.id", is(7)));
    }

    @Test
    public void updateAlumno_ShouldRender202() throws Exception {
        Long alumnoId = 1L;
        mockMvc.perform(post("/alumnos/{alumnoId}", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockUpdateAlumnoJson()))

                .andExpect(status().isAccepted())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/alumnos/1")))
                .andExpect(jsonPath("$.id", is(alumnoId.intValue())))
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
        mockMvc.perform(post("/alumnos/{alumnoId}", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockUpdateAlumnoWithOnlyDirectionJson()))

                .andExpect(status().isAccepted())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/alumnos/1")))
                .andExpect(jsonPath("$.id", is(alumnoId.intValue())))
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
        mockMvc.perform(post("/alumnos/{alumnoId}", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .content(mockUpdateAlumnoWithOnlyEmailJson()))

                .andExpect(status().isAccepted())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/alumnos/1")))
                .andExpect(jsonPath("$.id", is(alumnoId.intValue())))
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
        mockMvc.perform(post("/alumnos/{alumnoId}", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .content("{}"))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$[" + alumnoId + "].message", is("No se ha podido actualizar la información del alumno")));
    }

    @Test
    public void updateAlumno_MethodArgumentNotValidException_ShouldRender404View() throws Exception {
        Long alumnoId = 0L;
        mockMvc.perform(post("/alumnos/{alumnoId}", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .content("{}"))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$[" + alumnoId + "].message", is("No se ha podido actualizar la información del alumno")));
    }

    @Test
    public void deleteAlumno_AlumnoNoEncontradoException_ShouldRender404View() throws Exception {
        Long alumnoId = 0L;
        mockMvc.perform(delete("/alumnos/{alumnoId}", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$[" + alumnoId + "].message", is("No se encontró ningún alumno con id: " + alumnoId)));
    }

    @Test
    public void deleteAlumno_ShouldRender200View() throws Exception {
        mockMvc.perform(delete("/alumnos/{alumnoId}", 1L)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(status().isAccepted())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.dni", hasToString("00000000A")))
                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/alumnos/1")))
                .andExpect(jsonPath("$.links[1].rel", is("pfc")))
                .andExpect(jsonPath("$.links[1].href", is("http://localhost/pfcs/5")))
                .andExpect(jsonPath("$.id", is(1)))
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(get("/alumnos")
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.content", hasSize(6)));
    }

    @Test
    public void getPfcFromAlumno_ShouldRender_200() throws Exception {
        Long alumnoId = 1L;
        mockMvc.perform(get("/alumnos/{alumnoId}/pfc", alumnoId)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/pfcs/5")))
                .andExpect(jsonPath("$.links[1].rel", is("directores")))
                .andExpect(jsonPath("$.links[1].href", is("http://localhost/pfcs/5/directores")))
                .andExpect(jsonPath("$.id", is(5)));
    }


    @Test
    public void getPfcFromAlumno_AlumnoNoEncontradoException_ShouldRender_404() throws Exception {
        Long alumnoId = 10L;
        mockMvc.perform(get("/alumnos/{alumnoId}/pfc", alumnoId)
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
    public void getPfcFromAlumno_AlumnoSinPfcException_ShouldRender_404() throws Exception {
        Long alumnoId = 7L;
        mockMvc.perform(get("/alumnos/{alumnoId}/pfc", alumnoId)
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
                "            \"direccion\": {" +
                "               \"domicilio\": \"Domicilio actualizado\"," +
                "               \"localidad\": \"Localidad actualizada\"," +
                "               \"pais\": \"Pais actualizado\"," +
                "               \"codigoPostal\": \"00000\"}," +
                "            \"telefono\": \"999999999\"," +
                "            \"email\": \"actualizado@org.com\"}";
    }

    private String mockUpdateAlumnoWithOnlyDirectionJson() {
        return "{" +
                "            \"direccion\": {" +
                "               \"domicilio\": \"Domicilio actualizado\"," +
                "               \"localidad\": \"Localidad actualizada\"," +
                "               \"pais\": \"Pais actualizado\"," +
                "               \"codigoPostal\": \"00000\"}}";
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
