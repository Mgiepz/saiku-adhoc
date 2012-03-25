package org.saiku.adhoc.model.dto;

public class PrptSolutionFileInfo extends SolutionFileInfo {
	
	private String user;
	
	private String password;

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

}
