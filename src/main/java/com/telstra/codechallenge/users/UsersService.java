package com.telstra.codechallenge.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsersService {

  @Value("${users.base.url}")
  private String usersBaseUrl;

  private RestTemplate restTemplate;

  public UsersService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

	/**
	 * Returns a random repositories. Taken from
	 * https://api.github.com/search/repositories
	 * @param limit 
	 *
	 * @return - a UserAccountsResponse containing list of User accounts
	 */
	public UserAccountsResponse getUsers(String q, String sort, String order, Integer limit) {
		UserAccountsResponse myResponse =restTemplate
				.getForObject(usersBaseUrl + "?q=" + q + "&sort=" + sort + "&order=" + order, UserAccountsResponse.class);
		List<User> items = myResponse.getItems();
		if(items.size()>=limit) {
			items = items.subList(0, limit);
		}
		myResponse.setItems(items);
		return myResponse;
	}
}
