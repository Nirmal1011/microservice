package com.telstra.codechallenge.repositories;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootReposController {

  private SpringBootReposService springBootReposService;

  public SpringBootReposController(
      SpringBootReposService springBootReposService) {
    this.springBootReposService = springBootReposService;
  }

  @RequestMapping(path = "/repositories", method = RequestMethod.GET)
	public RepositoryResponse repositories(@RequestParam(value = "q", defaultValue = "created") String q,
			@RequestParam(value = "sort", defaultValue = "stars") String sort,
			@RequestParam(value = "order", defaultValue = "desc") String order,
			@RequestParam(value = "limit") Integer limit) {
    return springBootReposService.getRepositories(q, sort, order, limit);
  }
  
}
