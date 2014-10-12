package com.uma.informatica.persistence.repositories;

import com.uma.informatica.persistence.models.Profesor;
import com.uma.informatica.persistence.models.Profesor_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by rafa on 07/07/14.
 */
public class ProfesorSpecifications {

    public static Specification<Profesor> idInList(final List<Long> searchId) {

        return new Specification<Profesor>() {
            @Override
            public Predicate toPredicate(Root<Profesor> profesorRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return profesorRoot.get(Profesor_.id).in(searchId);
            }

        };
    }
}