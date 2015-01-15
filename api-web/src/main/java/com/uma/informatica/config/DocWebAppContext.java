package com.uma.informatica.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@ComponentScan( { "com.uma.informatica.controllers"})
public class DocWebAppContext extends WebMvcConfigurerAdapter {


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

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean //Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementation(){
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo());
//                .includePatterns(".*pet.*");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "My Apps API Title",
                "My Apps API Description",
                "My Apps API terms of service",
                "My Apps API Contact Email",
                "My Apps API Licence Type",
                "My Apps API License URL"
        );
        return apiInfo;
    }

    //        @Bean
//        public InternalResourceViewResolver getInternalResourceViewResolver() {
//            InternalResourceViewResolver resolver = new InternalResourceViewResolver();
////            resolver.setPrefix("/WEB-INF/pages/");
//            resolver.setSuffix(".jsp");
//            return resolver;
//        }

//        @Override
//        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//            configurer.enable();
//        }

//        private List<HttpMessageConverter<?>> messageConverters;
//
//        @Override
//        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//
//            // Note: this overwrites the default message converters.
//            converters.addAll(getMessageConverters());
//        }
//
//
//        /**
//         * The message converters for the content types we support.
//         *
//         * @return the message converters; returns the same list on subsequent calls
//         */
//        private List<HttpMessageConverter<?>> getMessageConverters() {
//
//            if (messageConverters == null) {
//                messageConverters = new ArrayList<HttpMessageConverter<?>>();
//
//                MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
//                mappingJacksonHttpMessageConverter.setObjectMapper(objectMapper());
//                messageConverters.add(mappingJacksonHttpMessageConverter);
//            }
//            return messageConverters;
//        }
//
//        @Bean // Also used in our integration tests.
//        public ObjectMapper objectMapper() {
//
//            return new ScalaObjectMapper();
//        }
}
