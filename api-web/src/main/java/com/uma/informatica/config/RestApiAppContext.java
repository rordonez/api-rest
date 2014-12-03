package com.uma.informatica.config;

import com.uma.informatica.persistence.configuration.ServiceContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by rafa on 15/06/14.
 */
@Configuration
@Import({ServiceContext.class, SocialContext.class, SecurityContext.class, WebAppContext.class, SecurityContextTestProfile.class, DocWebAppContext.class})
public class RestApiAppContext {
}
