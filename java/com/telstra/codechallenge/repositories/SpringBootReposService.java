package com.telstra.codechallenge.repositories;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.telstra.codechallenge.repositories.Item;
import com.telstra.codechallenge.repositories.RepositoryResponse;

@Service
public class SpringBootReposService {

  @Value("${repositories.base.url}")
  private String repoBaseUrl;

  private RestTemplate restTemplate;

  public SpringBootReposService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

	/**
	 * Returns a random repositories. Taken from
	 * https://api.github.com/search/repositories
	 * @param limit 
	 *
	 * @return - a UserAccountsResponse containing list of repositories
	 */
	public RepositoryResponse getRepositories(String q, String sort, String order, Integer limit) {
		RepositoryResponse myResponse = restTemplate
				.getForObject(repoBaseUrl + "?q=" + q + "&sort=" + sort + "&order=" + order, RepositoryResponse.class);
		List<Item> items = myResponse.getItems();
		ZonedDateTime dateToCompate = ZonedDateTime.now().minus(180, ChronoUnit.DAYS);
		List<Item> filteredItems = items.stream() // converting the list to stream
				.filter(i -> ZonedDateTime.parse(i.getCreatedAt()).isAfter(dateToCompate)) // filter the stream to
																							// create a new stream
				.collect(Collectors.toList());
		if(filteredItems.size()>=limit) {
			filteredItems = filteredItems.subList(0, limit);
		}
		myResponse.setItems(filteredItems);
		return myResponse;
	}
}
