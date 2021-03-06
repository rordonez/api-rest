package com.uma.informatica.persistence.configuration;

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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@EnableJpaRepositories(basePackages = "com.uma.informatica.persistence.repositories")
@ComponentScan(basePackages = {"com.uma.informatica.persistence.**"})
@PropertySource( value = "classpath:application-${spring.profiles.active}.properties" )
@EnableTransactionManagement
@Configuration
public class ServiceContext {

    @Bean
    public EntityManagerFactory entityManagerFactory(HibernateJpaVendorAdapter adapter, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.uma.informatica.persistence.models");
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(adapter);
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager( EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
