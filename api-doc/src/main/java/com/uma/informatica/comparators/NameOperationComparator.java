package com.uma.informatica.comparators;

import com.mangofactory.swagger.OperationComparator;
import com.wordnik.swagger.core.DocumentationOperation;
import org.springframework.stereotype.Component;

/**
 * Created by rafaordonez on 04/03/14.
 */
@Component
public class NameOperationComparator implements OperationComparator {
    @Override
    public int compare(DocumentationOperation first, DocumentationOperation second) {
        return first.getNickname().compareTo(second.getNickname());
    }
}