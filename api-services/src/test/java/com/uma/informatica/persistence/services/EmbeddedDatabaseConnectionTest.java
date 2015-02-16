package com.uma.informatica.persistence.services;

import com.uma.informatica.core.profiles.PropertyMockingApplicationContextInitializer;
import com.uma.informatica.persistence.configuration.EmbeddedDataSourceConfiguration;
import com.uma.informatica.persistence.configuration.ServiceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by rafaordonez on 10/02/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceContext.class, EmbeddedDataSourceConfiguration.class}, initializers = PropertyMockingApplicationContextInitializer.class)
@DirtiesContext
@ActiveProfiles("test")
public class EmbeddedDatabaseConnectionTest {

    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void verifyEmbeddedDatabase() {
        String name = jdbcTemplate.queryForObject("SELECT nombre FROM alumno WHERE dni LIKE '%73894276N%'", String.class);
        assertThat(name, is("Antonio"));
    }

}
