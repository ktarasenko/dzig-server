package com.dzig.utils;

public class RestException extends Exception {

	private static final long serialVersionUID = -6600500814546988111L;
	private final int statusCode;


	public RestException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	
}
