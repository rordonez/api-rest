package com.uma.informatica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa on 12/10/14.
 */
@Profile({"test", "doc"})
@Configuration
public class SecurityContextTestProfile {

    @Bean
    public FilterChainProxy springSecurityFilterChain()
            throws ServletException, Exception {

        List<SecurityFilterChain> securityFilterChains = new ArrayList<SecurityFilterChain>();
        securityFilterChains.add(new DefaultSecurityFilterChain(
                new AntPathRequestMatcher("/**")));
        return new FilterChainProxy(securityFilterChains);
    }
}
