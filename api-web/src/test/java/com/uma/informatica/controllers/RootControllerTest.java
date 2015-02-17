package com.uma.informatica.controllers;

import com.uma.informatica.config.RestApiAppContext;
import com.uma.informatica.core.profiles.PropertyMockingApplicationContextInitializer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by rafa on 17/02/15.
 */
@ContextConfiguration(classes = {RestApiAppContext.class}, initializers = PropertyMockingApplicationContextInitializer.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class RootControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getLins_ShouldRender_200() throws Exception {
        mockMvc.perform(get("/.json")
                .accept(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(status().isOk())
                .andExpect(content().contentType(IntegrationTestUtil.applicationJsonMediaType))

                .andExpect(jsonPath("$.links", hasSize(3)));
    }
}