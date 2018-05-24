package com.telstra.codechallenge.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.telstra.codechallenge.constants.*;
@RestController
public class RepositoryController {

	@Autowired
	private RepositoryService repositoryService;

	private static final Logger log = LoggerFactory.getLogger(RepositoryController.class);

	public RepositoryController(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.timeout.enabled", value = "false") }, fallbackMethod = "fallBackMethod")
	@RequestMapping(path = "/repositories", method = RequestMethod.GET)
	public ResponseEntity<?> repositories(@RequestParam(value = ServiceConstants.Q_PARAM, defaultValue = ServiceConstants.Q_VALUE) String q,
			@RequestParam(value = ServiceConstants.SORT_PARAM, defaultValue = ServiceConstants.SORT_VALUE) String sort,
			@RequestParam(value = ServiceConstants.ORDER_PARAM, defaultValue = ServiceConstants.ORDER_DESC_VALUE) String order,
			@RequestParam(value = ServiceConstants.LIMIT_PARAM) String limit) {
		try {
			return new ResponseEntity<>( repositoryService.getRepositories(q, sort, order, Integer.parseInt(limit)), HttpStatus.OK);
		} catch (NumberFormatException e) {
			log.error("NumberFormatException ocurred : " + e.getMessage());
			ErrorResponse myResponse = new ErrorResponse("Error", "Please provide numbers as a limit");
			return new ResponseEntity<>(myResponse, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> fallBackMethod(String q, String sort, String order, String limit) {
		ErrorResponse myResponse = new ErrorResponse("Error", "Service not available. Please try after sometime!");
		return new ResponseEntity<>(myResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}

}
