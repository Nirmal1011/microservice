package com.telstra.codechallenge.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.codechallenge.users.UserAccountsResponse;

@RestController
public class SpringBootUsersController {

  private SpringBootUsersService springBootUsersService;

  public SpringBootUsersController(
      SpringBootUsersService springBootUsersService) {
    this.springBootUsersService = springBootUsersService;
  }
  
  @RequestMapping(path = "/users", method = RequestMethod.GET)
	public UserAccountsResponse users(@RequestParam(value = "q", defaultValue = "followers:0") String q,
			@RequestParam(value = "sort", defaultValue = "stars") String sort,
			@RequestParam(value = "order", defaultValue = "desc") String order,
			@RequestParam(value = "limit") Integer limit) {
    return springBootUsersService.getUsers(q, sort, order, limit);
  }
}
