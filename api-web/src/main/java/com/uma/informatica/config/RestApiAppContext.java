package com.uma.informatica.config;

import com.uma.informatica.persistence.configuration.ServiceContext;
import org.springframework.context.annotation.Import;

@Import({ServiceContext.class,  WebAppContext.class, DocWebAppContext.class})
public class RestApiAppContext {}
