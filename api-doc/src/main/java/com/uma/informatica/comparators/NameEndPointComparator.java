package com.uma.informatica.comparators;

import com.mangofactory.swagger.EndpointComparator;
import com.wordnik.swagger.core.DocumentationEndPoint;
import org.springframework.stereotype.Component;

/**
 * Created by rafaordonez on 04/03/14.
 */
@Component
public class NameEndPointComparator implements EndpointComparator {
    @Override
    public int compare(DocumentationEndPoint first, DocumentationEndPoint second) {
        return first.getPath().compareTo(second.getPath());
    }
}