package com.uma.informatica.controllers;

import com.uma.informatica.config.RestApiAppContext;
import com.uma.informatica.core.profiles.PropertyMockingApplicationContextInitializer;
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

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by rafa on 19/02/15.
 */
@ContextConfiguration(classes = {RestApiAppContext.class}, initializers = PropertyMockingApplicationContextInitializer.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ProfesorControllerRestTest extends AbstractTransactionalJUnit4SpringContextTests {

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
    public void getAllWithIds_ShouldRender_200() throws Exception {
        mockMvc.perform(get("/profesores.json?ids=1&ids=2")
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].links", hasSize(1)))
                .andExpect(jsonPath("$.content[0].links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.content[0].links[0].href", endsWith("/profesores/1")))
                .andExpect(jsonPath("$.content[1].links", hasSize(1)))
                .andExpect(jsonPath("$.content[1].links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.content[1].links[0].href", endsWith("/profesores/2")))

                .andExpect(jsonPath("$.links", hasSize(0)));
    }

    @Test
    public void getAll_ShouldRender_200() throws Exception {
        mockMvc.perform(get("/profesores.json")
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))
                .andExpect(jsonPath("$.content", hasSize(3)))

                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel", is(Link.REL_SELF)))
                .andExpect(jsonPath("$.links[0].href", endsWith("/profesores{?page,size,sort}")))

                .andExpect(jsonPath("$.page.totalElements", is(3)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.size", is(10)));
    }
}
