package com.uma.informatica.persistence.repositories;

import com.uma.informatica.persistence.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import java.util.List;

/**
 *
 * Base services for persisting {@link com.uma.informatica.persistence.models.User} users
 *
 */
@RestResource (path = "users", rel = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUsername(@Param("username") String username);

	List<User> findUsersByFirstNameOrLastNameOrUsername(
			@Param("firstName") String firstName,
            @Param("lastName") String lastName,
			@Param("username") String username);

}
