package com.telstra.codechallenge.repositories;

public class ErrorResponse {
	public String status;
	public String description;
	public ErrorResponse() {}
	public ErrorResponse(String status, String description) {
		this.status = status;
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
