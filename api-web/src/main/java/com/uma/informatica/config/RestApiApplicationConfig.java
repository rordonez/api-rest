package com.uma.informatica.config;

import com.uma.informatica.persistence.configuration.ServiceContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.io.File;

/**
 * Initializes the config application. This is a programmatic equivalent to {@literal config.xml}. {@link
 * AbstractAnnotationConfigDispatcherServletInitializer} sets up the Servlet-3.0 application <EM>and</EM> bootstraps the
 * main {@link org.springframework.context.ApplicationContext application context} instance that powers the Spring MVC
 * application.
 *
 * @author Josh Long
 */
public class RestApiApplicationConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("spring.profiles.active", "test");
        registerProxyFilter(servletContext, "springSecurityFilterChain");
       // registerProxyFilter(servletContext, "oAuth2AuthenticationProcessingFilter");
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
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        File uploadDirectory = ServiceContext.CRM_STORAGE_UPLOADS_DIRECTORY;
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadDirectory.getAbsolutePath(), maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);
        registration.setMultipartConfig(multipartConfigElement);
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