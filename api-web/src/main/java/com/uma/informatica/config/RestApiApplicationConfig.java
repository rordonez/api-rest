package com.uma.informatica.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;


/**
 *
 * Configuración de la aplicación. Esta clase es equivalente a {@literal config.xml}. {@link
 * AbstractAnnotationConfigDispatcherServletInitializer} configura la aplicación para que utilice Servlet-3.0 y arranca
 * la instancia del contexto de la aplicación del que se nutre Spring MVC.
 *
 * @author Rafa Ordoñez
 */
public class RestApiApplicationConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{RestApiAppContext.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     *
     * Este método configura el DispatcherServlet para que acepte parametros adicionales de configuración.
     * Se han definido dos:
     *   - el parametro dispatchOptionsRequest habilita al Servlet para que acepte peticiones OPTIONS.
     *   - el parámetro spring.profiles.active configura Spring para que use el perfil que se le pasa como variable
     *     de entorno.
     *
     * @param registration
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("dispatchOptionsRequest", "true");
        configureProfiles(registration);
    }

    private void configureProfiles(ServletRegistration.Dynamic registration) {
        String springProfile = System.getProperty("profile.property");
        if(springProfile == null) {
            springProfile = "test";
        }
        registration.setInitParameter("spring.profiles.active", springProfile);
    }

    /**
     *
     * Carga los filtros necesarios para procesar la peticiones de nuestra aplicación.
     * Se han definido dos filtros:
     *   - Configuración del encoding a UTF-8
     *   - Generación de cabeceras ETag
     *
     * @return
     */

    @Override
    protected Filter[] getServletFilters() {

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();
        return new Filter[] { characterEncodingFilter, shallowEtagHeaderFilter};
    }

}

