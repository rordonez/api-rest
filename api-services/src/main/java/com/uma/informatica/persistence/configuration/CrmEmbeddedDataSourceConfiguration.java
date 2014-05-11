package com.uma.informatica.persistence.configuration;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by rafaordonez on 09/02/14.
 */
@Configuration
@Profile({"crm"})
class CrmEmbeddedDataSourceConfiguration {

    private Log log = LogFactory.getLog(getClass());

    @PostConstruct
    public void setupTestProfileImages() throws Exception {
        long userId = 5;
        File profilePhotoForUser5 = new File(ServiceConfiguration.CRM_STORAGE_PROFILES_DIRECTORY, Long.toString(userId));
        if (!profilePhotoForUser5.exists()) {
            // copy the profile photo back
            String pathForProfilePhoto = "/sample-photos/spring-dog-2.png";
            ClassPathResource classPathResource = new ClassPathResource(pathForProfilePhoto);
            Assert.isTrue(classPathResource.exists(), "the resource " + pathForProfilePhoto + " does not exist");
            OutputStream outputStream = new FileOutputStream(profilePhotoForUser5);
            InputStream inputStream = classPathResource.getInputStream();
            try {
                IOUtils.copy(inputStream, outputStream);
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);
            }
            log.debug("setup photo " + profilePhotoForUser5.getAbsolutePath() + " for the sample user #" + Long.toString(userId) + "'s profile photo.");
        }

        if (!profilePhotoForUser5.exists()) {
            throw new RuntimeException("couldn't setup profile photos at " + profilePhotoForUser5.getAbsolutePath() + ".");
        }
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.H2);
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        return adapter;
    }

    /**
     * You can access this H2 database at <a href = "http://localhost:8080/admin/console">the H2 administration
     * console</a>.
     */
    @Bean
    public DataSource dataSource() {

        ClassPathResource classPathResource = new ClassPathResource("/crm-schema-h2.sql");

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(classPathResource);

        EmbeddedDatabaseFactoryBean embeddedDatabaseFactoryBean = new EmbeddedDatabaseFactoryBean();
        embeddedDatabaseFactoryBean.setDatabasePopulator(resourceDatabasePopulator);
        embeddedDatabaseFactoryBean.setDatabaseName(ServiceConfiguration.RESTAPI_NAME);
        embeddedDatabaseFactoryBean.setDatabaseType(EmbeddedDatabaseType.H2);
        embeddedDatabaseFactoryBean.afterPropertiesSet();
        return embeddedDatabaseFactoryBean.getObject();
    }

}