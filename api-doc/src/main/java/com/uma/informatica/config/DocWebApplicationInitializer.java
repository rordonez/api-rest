package com.uma.informatica.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Initializes the config application for Swagger Documentation
 *
 * @author Rafa Ordoñez
 */
public class DocWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ WebAppConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }

    @Configuration
    @EnableWebMvc
    @ComponentScan( { "com.uma.informatica.controllers", "com.uma.informatica.config"})
    public static class WebAppConfig extends WebMvcConfigurerAdapter {

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
}
