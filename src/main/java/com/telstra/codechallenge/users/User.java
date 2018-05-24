package com.telstra.codechallenge.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	@JsonProperty("html_url")
	private String htmlUrl;
	private Long id;
	private String login;
	public User() {
	}
	public String getHtmlUrl() {
		return htmlUrl;
	}
	public Long getId() {
		return id;
	}
	public String getLogin() {
		return login;
	}
}
