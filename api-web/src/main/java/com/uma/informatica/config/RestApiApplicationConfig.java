package com.uma.informatica.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


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
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        String springProfile = System.getProperty("profile.property");
        if(springProfile == null) {
            springProfile = "test";
        }
        servletContext.setInitParameter("spring.profiles.active", springProfile);
        registerProxyFilter(servletContext, "springSecurityFilterChain");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RestApiAppContext.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{RestApiAppContext.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] { characterEncodingFilter};
    }

    private void registerProxyFilter(ServletContext servletContext, String name) {
        DelegatingFilterProxy filter = new DelegatingFilterProxy(name);
        filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
        servletContext.addFilter(name, filter).addMappingForUrlPatterns(null, false, "/*");
    }

}

