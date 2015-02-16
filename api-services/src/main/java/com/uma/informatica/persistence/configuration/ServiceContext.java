package com.uma.informatica.persistence.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Profile({"test", "doc", "production"})
@EnableJpaRepositories(basePackages = "com.uma.informatica.persistence.repositories")
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.uma.informatica.persistence.configuration", "com.uma.informatica.persistence.services"})
@PropertySource( value = "classpath:application-${spring.profiles.active}.properties" )
@Configuration
public class ServiceContext {

    public static final String RESTAPI_NAME = "restapi";

    @Autowired
    DataSource dataSource;

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
