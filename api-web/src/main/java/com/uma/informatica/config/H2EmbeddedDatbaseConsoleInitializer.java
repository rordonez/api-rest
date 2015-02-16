package com.uma.informatica.config;


import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Carga en <A href="http://localhost:8080/h2/"> una consola de configuración H2</A>.
 * <p/>
 * Para acceder a la base de datos de esta aplicación usar  JDBC URI {@code jdbc:h2:mem:informatica}.
 *
 * @author Josh Long
 */
public class H2EmbeddedDatbaseConsoleInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebServlet webServlet = new WebServlet();

        ServletRegistration.Dynamic dynamic = servletContext.addServlet("h2", webServlet);
        dynamic.setInitParameter("trace", "true");
        dynamic.setAsyncSupported(true);
        dynamic.addMapping("/h2/*");
        dynamic.setLoadOnStartup(1);
    }

}
