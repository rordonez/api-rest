package com.uma.informatica.controllers.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rafa on 17/02/15.
 */
public class ControllerUtils {

    public static ResponseEntity<Void> allows(HttpMethod... methods) {
        HttpHeaders headers = new HttpHeaders();
        Set<HttpMethod> allow = new HashSet<>();
        for(HttpMethod method: methods){
            allow.add(method);
        }
        headers.setAllow(allow);

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
