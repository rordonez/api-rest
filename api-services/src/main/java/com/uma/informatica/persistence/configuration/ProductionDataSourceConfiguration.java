package com.uma.informatica.persistence.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * Created by rafaordonez on 04/02/14.
 */

@Configuration
@Profile({"production"})
public class ProductionDataSourceConfiguration {

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        return adapter;
    }

    @Bean
    public DataSource dataSource(Environment env) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(env.getPropertyAsClass("dataSource.driverClass", Driver.class));
        dataSource.setUrl(env.getProperty("dataSource.url").trim());
        dataSource.setUsername(env.getProperty("dataSource.user").trim());
        dataSource.setPassword(env.getProperty("dataSource.password").trim());
        return dataSource;
    }
}
