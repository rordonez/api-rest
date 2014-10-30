package com.uma.informatica.controllers;

import com.uma.informatica.config.RestApiAppContext;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

}
