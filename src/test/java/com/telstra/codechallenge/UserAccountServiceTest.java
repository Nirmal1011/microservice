package com.telstra.codechallenge;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.telstra.codechallenge.repositories.ErrorResponse;
import com.telstra.codechallenge.users.UserAccountsResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserAccountServiceTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void userAccountsTest() {
		UserAccountsResponse responseBody = this.restTemplate.getForObject("http://localhost:8080/users?limit=5",
				UserAccountsResponse.class);
		assertThat(responseBody.getItems().size()).isEqualByComparingTo(5);
		assertThat(responseBody.getItems().get(0).getId()).isEqualByComparingTo(44L);
		assertThat(responseBody.getItems().get(0).getLogin()).isEqualToIgnoringCase("errfree");
		assertThat(responseBody.getItems().get(0).getHtmlUrl()).isEqualToIgnoringCase("https://github.com/errfree");
	}
	
	@Test
	public void userAccountInvalidParamTest() {
		ErrorResponse responseBody = this.restTemplate.getForObject("http://localhost:8080/users?limit=abc",
				ErrorResponse.class);
		assertThat(responseBody.getStatus()).isEqualToIgnoringCase("error");
	}
	
}
