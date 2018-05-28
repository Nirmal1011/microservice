package com.telstra.codechallenge.repositories;

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

@RestController
public class RepositoryController {
	
	public static Logger logger = Logger.getLogger(RepositoryController.class);

	@Autowired
	private RepositoryService repositoryService;

	public RepositoryController(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.timeout.enabled", value = "false") }, fallbackMethod = "fallBackMethod")
	@RequestMapping(path = "/repositories", method = RequestMethod.GET)
	public ResponseEntity<?> repositories(
			@RequestParam(value = ServiceConstants.Q_PARAM, defaultValue = ServiceConstants.Q_VALUE) String q,
			@RequestParam(value = ServiceConstants.SORT_PARAM, defaultValue = ServiceConstants.SORT_VALUE) String sort,
			@RequestParam(value = ServiceConstants.ORDER_PARAM, defaultValue = ServiceConstants.ORDER_DESC_VALUE) String order,
			@RequestParam(value = ServiceConstants.LIMIT_PARAM) String limit) {
		try {
			if (Integer.parseInt(limit) <= 0) {
				logger.error("Not a positive integer");
				ErrorResponse myResponse = new ErrorResponse("Error",
						"Please provide a positive integer value as a limit");
				return new ResponseEntity<>(myResponse, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(repositoryService.getRepositories(q, sort, order, Integer.parseInt(limit)),
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
