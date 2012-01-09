package org.saiku.adhoc.model.dto;

/**
 * This DTO wraps any object returned from the server 
 * to be able to return a meaningfull error on failure.
 * 
 * @author mgie
 *
 */
public class SaikuAdhocResponse {
	
	public SaikuAdhocResponse(String error, Object data) {
		super();
		this.error = error;
		this.data = data;
	}

	private String error;
	
	private Object data;

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

}
