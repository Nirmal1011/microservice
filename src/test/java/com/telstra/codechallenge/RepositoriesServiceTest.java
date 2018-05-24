package com.telstra.codechallenge;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.telstra.codechallenge.repositories.ErrorResponse;
import com.telstra.codechallenge.repositories.RepositoryResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RepositoriesServiceTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Value("${repositories.base.url}")
	private String repoBaseUrl;

	@Test
	public void userAccountsTest() {
		RepositoryResponse responseBody = this.restTemplate.getForObject(prepareUrl("/repositories?limit=3"),
				RepositoryResponse.class);
		assertThat(responseBody.getItems().size()).isEqualByComparingTo(2);
		responseBody.getItems().forEach(item -> {
			assertThat(item.getDescription()).isNotNull();
			assertThat(item.getHtmlUrl()).isNotNull();
			assertThat(item.getLanguage()).isNotNull();
			assertThat(item.getName()).isNotNull();
			assertThat(item.getWatchersCount()).isNotNull();
		});
	}

	@Test
	public void userAccountInvalidParamTest() {
		ErrorResponse responseBody = this.restTemplate.getForObject(prepareUrl("/users?limit=abc"),
				ErrorResponse.class);
		assertThat(responseBody.getStatus()).isEqualToIgnoringCase("error");
	}

	private String prepareUrl(String url) {
		return repoBaseUrl + url;
	}

}
