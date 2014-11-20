package com.uma.informatica.controllers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;

import javax.transaction.Transactional;

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

import com.uma.informatica.config.RestApiAppContext;

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

}
