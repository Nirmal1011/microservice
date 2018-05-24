package com.telstra.codechallenge.repositories;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RepositoryService {

  @Value("${repositories.base.url}")
  private String repoBaseUrl;

  @Autowired
  private RestTemplate restTemplate;
  
  private static final Logger log = LoggerFactory.getLogger(RepositoryService.class);

  public RepositoryService(RestTemplate restTemplate) {
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
				.getForObject("https://api.github.com/search/repositories" + "?q=" + q + "&sort=" + sort + "&order=" + order, RepositoryResponse.class);
		List<Item> items = myResponse.getItems();
		log.info("Items size before filteration : " + myResponse.getItems().size());
		ZonedDateTime dateToCompate = ZonedDateTime.now().minus(180, ChronoUnit.DAYS);
		List<Item> filteredItems = items.stream() // converting the list to stream
				.filter(i -> ZonedDateTime.parse(i.getCreatedAt()).isAfter(dateToCompate)) // filter the stream to
																							// create a new stream
				.collect(Collectors.toList());
		log.info("Items size after filteration : " + filteredItems.size());
		if(filteredItems.size()>=limit) {
			filteredItems = filteredItems.subList(0, limit);
		}
		filteredItems.forEach(item -> item.setCreatedAt(null));
		myResponse.setItems(filteredItems);
		return myResponse;
	}
}
