package com.uma.informatica.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 *
 * Esta clase configura la aplicación Web. Se define la negociación de contenido, las piezas que procesan el formato
 * de entrada y salida de la información, motores de plantillas, ect.
 *
 * Created by rafa on 14/06/14.
 */
@Profile({"test", "production"})
@Configuration
@EnableWebMvc
@ComponentScan({"com.uma.informatica.**"})
@EnableSpringDataWebSupport
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class WebAppContext extends WebMvcConfigurerAdapter {

    /**
     *
     * Configuración necesaria para la Negociación de Contenido. Sólo se tiene en cuenta el sufijo en la URI de la
     * petición al recurso
     *
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).
                ignoreAcceptHeader(false).
                useJaf(false).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("xml", MediaType.APPLICATION_XML).
                mediaType("hal", MediaTypes.HAL_JSON).
                mediaType("json", MediaType.APPLICATION_JSON);
    }

    /**
     *
     * Configura los MessageConverters para usar Jackson2ObjectMapperBuilder. Esta clase proporciona una API para
     * configurar Jackson reteniendo la configuración por defecto de Spring MVC. También crea instancias de un
     * ObjectMapper y un XMLMapper en la misma instancia.
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .failOnEmptyBeans(false);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        converters.add(new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build()));
    }

}
