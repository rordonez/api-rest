package com.uma.informatica.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.uma.informatica.config.RestApiAppContext;

/**
 * Created by rafa on 01/12/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestApiAppContext.class}, initializers = PropertyMockingApplicationContextInitializer.class)
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ActiveProfiles("test")
public class PfcControllerRestTest {

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
    public void getAll_ShouldRender200() throws Exception {
        mockMvc.perform(get("/pfcs")
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/pfcs")))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[2].id", is(3)))
                .andExpect(jsonPath("$.content[3].id", is(4)))
                .andExpect(jsonPath("$.content[4].id", is(5)));
    }

    @Test
    public void searchAlumnos_ShouldRender_200() throws Exception {
        mockMvc.perform(get("/pfcs?estado=EMPEZADO&search=true")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.links", hasSize(0)))
                .andExpect(jsonPath("$.content[0].id", is(4)))
                .andExpect(jsonPath("$.content[1].id", is(5)));

    }

    @Test
    public void searchAlumnos_PfcNoEncontradoException_ShouldRender_404() throws Exception {
        mockMvc.perform(get("/pfcs?search=true")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isNotFound())

                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$[0].message", is("No se encontró ningún pfc")));

    }

    @Test
    public void createPfc_MethodArgumentNotValidException_ShouldRender400() throws Exception {

        mockMvc.perform(put("/pfcs")
                .accept(IntegrationTestUtil.applicationJsonMediaType)
                .contentType(IntegrationTestUtil.applicationJsonMediaType)
                .locale(new Locale("ES"))
                .content(mockPfcJsonWithoutDirectores()))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(IntegrationTestUtil.vndErrorMediaType))
                .andExpect(jsonPath("$.links", hasSize(0)))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    private String mockPfcJsonWithoutDirectores() {
        return "{";
    }


    //    private String mockSearchPfcsAlumnoNoEncontradoException() {
//        return "{" +
//                    "\"nombre\":\"Tecnologías de la Innovación\"," +
//                    "\"estado\":\"FINALIZADO\"," +
//                    "\"departamento\":\"Lenguajes y Ciencias de la Computacion\"," +
//                "fechaFin":null,
//                "links":[
//            {
//                "rel":"self",
//                    "href":"http:\/\/localhost\/pfcs\/1"
//            },
//            {
//                "rel":"directores",
//                    "href":"http:\/\/localhost\/pfcs\/1\/directores"
//            }
//            ],
//            "directorAcademico":null,
//                "fechaInicio":"2001-09-266"
//        }
//    }
}
