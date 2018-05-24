package com.telstra.codechallenge.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccountsResponse {
	private List<User> items;
	public List<User> getItems() {
		return items;
	}
	public void setItems(List<User> users) {
		this.items = users;
	}
}
