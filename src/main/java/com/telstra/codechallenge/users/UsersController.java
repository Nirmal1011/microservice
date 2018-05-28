package com.telstra.codechallenge.users;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.telstra.codechallenge.constants.ServiceConstants;
import com.telstra.codechallenge.repositories.ErrorResponse;

@RestController
public class UsersController {
	
	public static Logger logger = Logger.getLogger(UsersController.class);

	@Autowired
	private UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.timeout.enabled", value = "false") }, fallbackMethod = "fallBackMethod")
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public ResponseEntity<?> users(@RequestParam(value = ServiceConstants.Q_PARAM, defaultValue = ServiceConstants.Q_FOLLOWER_VALUE) String q,
			@RequestParam(value = ServiceConstants.SORT_PARAM, defaultValue = ServiceConstants.SORT_USER_VALUE) String sort,
			@RequestParam(value = ServiceConstants.ORDER_PARAM, defaultValue = ServiceConstants.ORDER_ASC_VALUE) String order,
			@RequestParam(value = ServiceConstants.LIMIT_PARAM) String limit) {
		try {
			if (Integer.parseInt(limit) <= 0) {
				logger.error("Not a positive integer");
				ErrorResponse myResponse = new ErrorResponse("Error",
						"Please provide a positive integer value as a limit");
				return new ResponseEntity<>(myResponse, HttpStatus.BAD_REQUEST);
			} else {
			return new ResponseEntity<>(usersService.getUsers(q, sort, order, Integer.parseInt(limit)),
					HttpStatus.OK);
			}
		} catch (NumberFormatException e) {
			logger.error("Not a numeric value");
			ErrorResponse myResponse = new ErrorResponse("Error", "Please provide numeric value as a limit");
			return new ResponseEntity<>(myResponse, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> fallBackMethod(String q, String sort, String order, String limit) {
		ErrorResponse myResponse = new ErrorResponse("Error", "Service not available. Please try after sometime!");
		return new ResponseEntity<>(myResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
