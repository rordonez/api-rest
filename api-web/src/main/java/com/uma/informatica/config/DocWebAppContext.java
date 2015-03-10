package com.uma.informatica.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by rafa on 02/12/14.
 */

@Profile({"doc"})
@Configuration
@EnableWebMvc
@EnableSwagger
@ComponentScan(value = {"com.uma.informatica.**"})
@EnableSpringDataWebSupport
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class DocWebAppContext extends WebMvcConfigurerAdapter {


    /**
     *
     * Añade manejadores para servir recursos estáticos
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/lib/**").addResourceLocations("/lib/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    /** CONFIGURACION SWAGGER **/
    @Bean
    public SwaggerSpringMvcPlugin customImplementation(SpringSwaggerConfig springSwaggerConfig){
        return new SwaggerSpringMvcPlugin(springSwaggerConfig)
                .ignoredParameterTypes(Pageable.class, PagedResourcesAssembler.class)
                .apiInfo(new ApiInfo(
                        "Desarrollo de una API Restful",
                        "Página para documentar la API REST",
                        null,
                        null,
                        null,
                        null
                ));
    }


}
