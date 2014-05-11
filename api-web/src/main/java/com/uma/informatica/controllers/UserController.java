package com.uma.informatica.controllers;

import com.uma.informatica.persistence.models.Customer;
import com.uma.informatica.persistence.models.User;
import com.uma.informatica.persistence.services.CrmService;
import com.uma.informatica.web.ApiUrls;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.util.ArrayList;

/**
 * Handles {@link com.uma.informatica.persistence.models.User} user entities.
 *
 * @author Josh Long
 */
@Controller
@RequestMapping (value = ApiUrls.ROOT_URL_USERS,
				 produces = MediaType.APPLICATION_JSON_VALUE)
class UserController {

	private CrmService crmService;
	
	UserController(){}
	
	@Inject
	public UserController(CrmService crmService) {
		this.crmService = crmService;
	}

	@RequestMapping (method = RequestMethod.DELETE, value = ApiUrls.URL_USERS_USER)
	@ResponseBody
    User deleteUser(@PathVariable Long user) {
		return crmService.removeUser(user);
	}

	@RequestMapping (method = RequestMethod.GET, value = ApiUrls.URL_USERS_USER)
	@ResponseBody
	User loadUser(@PathVariable Long user) {
		return crmService.findById(user);
	}

	@RequestMapping (method = RequestMethod.GET, value = ApiUrls.URL_USERS_USER_CUSTOMERS)
	@ResponseBody
	CustomerList loadUserCustomers(@PathVariable Long user) {
		CustomerList customerResourceCollection = new CustomerList();
		customerResourceCollection.addAll(this.crmService.loadCustomerAccounts(user));
		return customerResourceCollection;
	}

	@RequestMapping (method = RequestMethod.GET, value = ApiUrls.URL_USERS_USER_CUSTOMERS_CUSTOMER)
	@ResponseBody
    Customer loadSingleUserCustomer(@PathVariable Long user, @PathVariable Long customer) {
		return crmService.findCustomerById(customer);
	}

	/**
	 * This is superior to using an {@link ArrayList} of {@link Customer} because it bakes
	 * in the generic type information which would've otherwise been lost and helps
	 * Jackson in the conversion at runtime.
	 */
	static class CustomerList extends ArrayList<Customer> {

		private static final long serialVersionUID = 1L;

	}
}
