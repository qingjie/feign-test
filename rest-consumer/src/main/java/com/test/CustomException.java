package com.test;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

	private String message;

	private String statusCode;

	private HttpStatus status;

	public CustomException(String message, String statusCode, HttpStatus status) {
		super(message);
		this.message = message;
		this.statusCode = statusCode;
		this.status = status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
