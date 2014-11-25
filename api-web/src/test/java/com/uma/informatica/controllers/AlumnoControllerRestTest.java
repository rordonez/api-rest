package com.uma.informatica.controllers;

import com.uma.informatica.config.RestApiAppContext;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by rafa on 30/10/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestApiAppContext.class})
@WebAppConfiguration
@Transactional
@ActiveProfiles("test")
public class AlumnoControllerRestTest {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetAlumnos() throws Exception {
        mockMvc.perform(get("/alumnos")
        		.accept(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.content", hasSize(7)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[2].id", is(3)))
                .andExpect(jsonPath("$.content[3].id", is(4)))
                .andExpect(jsonPath("$.content[4].id", is(5)))
                .andExpect(jsonPath("$.content[5].id", is(6)))
                .andExpect(jsonPath("$.content[6].id", is(7)))
                .andExpect(jsonPath("$.content[6].pfc", nullValue()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createAlumno_ShouldRender201View() throws Exception {
        Alumno alumno = new Alumno();
        alumno.setDni("12345678A");
        alumno.setNombre("Nombre");
        alumno.setApellidos("Apellidos");
        alumno.setTitulacion(TitulacionEnum.GESTION);
        alumno.setDomicilio("Domicilio");
        alumno.setLocalidad("Localidad");
        alumno.setPais("Pais");
        alumno.setTelefono("666666666");
        alumno.setCodigoPostal("12345");
        alumno.setEmail("example@org.com");
        alumno.setFechaNacimiento(new Date());

        mockMvc.perform(put("/alumnos", alumno)
                .accept(IntegrationTestUtil.APPLICATION_JSON_UTF8)
                .contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(alumno)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/alumnos/8")))
                .andExpect(jsonPath("$.id", is(8)))
                .andExpect(jsonPath("$.dni", is(alumno.getDni())))
                .andExpect(jsonPath("$.nombre", is(alumno.getNombre())))
                .andExpect(jsonPath("$.apellidos", is(alumno.getApellidos())))
                .andExpect(jsonPath("$.titulacion", is(alumno.getTitulacion().name())))
                .andExpect(jsonPath("$.domicilio", is(alumno.getDomicilio())))
                .andExpect(jsonPath("$.localidad", is(alumno.getLocalidad())))
                .andExpect(jsonPath("$.pais", is(alumno.getPais())))
                .andExpect(jsonPath("$.telefono", is(alumno.getTelefono())))
                .andExpect(jsonPath("$.codigoPostal", is(alumno.getCodigoPostal())))
                .andExpect(jsonPath("$.email", is(alumno.getEmail())))
                .andExpect(jsonPath("$.fechaNacimiento", is(new SimpleDateFormat("YYYY-MM-DD").format(alumno.getFechaNacimiento()))))
                .andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void findById_AlumnoNoEncontradoException_ShouldRender404View() throws Exception {
    	mockMvc.perform(get("/alumnos/{alumnoId}", 0L)
    			.accept(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
    			.andExpect(status().isNotFound())
    			//.andExpect(content().encoding("UTF-8"))
    			.andExpect(jsonPath("$[0].message", is("alumno#0 was not found")))
    			.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById_ShouldRender200View() throws Exception {
        mockMvc.perform(get("/alumnos/{alumnoId}", 1L)
                .accept(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.dni", hasToString("00000000A")))
                .andExpect(jsonPath("$.links", hasSize(2)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/alumnos/1")))
                .andExpect(jsonPath("$.links[1].rel", is("pfc")))
                .andExpect(jsonPath("$.links[1].href", is("http://localhost/pfcs/5")))
                .andExpect(jsonPath("$.id", is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById_WithoutPfc_ShouldRender200View() throws Exception {
        mockMvc.perform(get("/alumnos/{alumnoId}", 7L)
                .accept(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.dni", hasToString("sadg")))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/alumnos/7")))
                .andExpect(jsonPath("$.id", is(7)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteAlumno_AlumnoNoEncontradoException_ShouldRender404View() throws Exception {
        mockMvc.perform(delete("/alumnos/{alumnoId}", 0L)
                .accept(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].message", is("alumno#0 was not found")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteAlumno_ShouldRender200View() throws Exception {
        mockMvc.perform(delete("/alumnos/{alumnoId}", 1L)
                .accept(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
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
                .accept(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.content", hasSize(6)));
    }
}
