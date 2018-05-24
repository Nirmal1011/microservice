package com.telstra.codechallenge.repositories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
	@JsonProperty("watchers_count")
	private Long watchersCount;
	private String language;
	@JsonProperty("html_url")
	private String htmlUrl;
	private String description;
	private String name;
	@JsonProperty("created_at")
	private String createdAt;
	
	public Item() {
	}
	public Long getWatchersCount() {
		return watchersCount;
	}
	public void setWatchersCount(Long watchersCount) {
		this.watchersCount = watchersCount;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHtmlUrl() {
		return htmlUrl;
	}
	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
