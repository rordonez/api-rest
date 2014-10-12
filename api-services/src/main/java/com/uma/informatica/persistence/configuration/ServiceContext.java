package com.uma.informatica.persistence.configuration;

import org.apache.commons.lang.SystemUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.File;

@EnableJpaRepositories(basePackages = "com.uma.informatica.persistence.repositories")
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.uma.informatica.persistence.configuration", "com.uma.informatica.persistence.services"})
@PropertySource("/config.properties")
@Configuration
public class ServiceContext {

    public static final String RESTAPI_NAME = "restapi";
    /**
     * The root directory to which all uploads for the application are uploaded.
     */
    public static final File RESTAPI_STORAGE_DIRECTORY = new File(SystemUtils.getUserHome(), RESTAPI_NAME);
    /**
     * Things are first uploaded by the application server to this directory. it's a sort
     * of staging directory
     */
    public static final File CRM_STORAGE_UPLOADS_DIRECTORY = new File(RESTAPI_STORAGE_DIRECTORY, "uploads");
    /**
     * When a profile photo is uploaded, the resultant, completely uploaded image is
     * stored in this directory
     */
    public static final File CRM_STORAGE_PROFILES_DIRECTORY = new File(RESTAPI_STORAGE_DIRECTORY, "profiles");

    @Inject
    DataSource dataSource;

    @PostConstruct
    public void setupStorage() throws Throwable {
        File[] files = {RESTAPI_STORAGE_DIRECTORY, CRM_STORAGE_UPLOADS_DIRECTORY, CRM_STORAGE_PROFILES_DIRECTORY};
        for (File f : files) {
            if (!f.exists() && !f.mkdirs()) {
                String msg = String.format("you must create the profile photos directory ('%s') " +
                        "and make it accessible to this process. Unable to do so from this process.", f.getAbsolutePath());
                throw new RuntimeException(msg);
            }
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(HibernateJpaVendorAdapter adapter, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.uma.informatica.persistence.models");
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(adapter);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager( EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
