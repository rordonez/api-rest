package com.uma.informatica.config;

import com.mangofactory.swagger.EndpointComparator;
import com.mangofactory.swagger.OperationComparator;
import com.mangofactory.swagger.configuration.DocumentationConfig;
import com.uma.informatica.comparators.NameEndPointComparator;
import com.uma.informatica.comparators.NameOperationComparator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by rafaordonez on 04/03/14.
 */

@Configuration
@Import(DocumentationConfig.class)
@PropertySource("classpath:swagger.properties")
public class CustomDocumentationConfig {

    @Bean
    public EndpointComparator endPointComparator() {
        return new NameEndPointComparator();
    }

    @Bean
    public OperationComparator operationComparator() {
        return new NameOperationComparator();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}