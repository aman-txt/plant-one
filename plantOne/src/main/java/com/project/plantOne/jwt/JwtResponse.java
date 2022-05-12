package com.project.plantOne.jwt;

public class JwtResponse {

	private String user_id;
	private String username;
	private String jwtToken;

	public JwtResponse(String user_id, String username, String jwtToken) {
		this.user_id = user_id;
		this.username = username;
		this.jwtToken = jwtToken;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	@Override
	public String toString() {

		String finalToken = "{" +
				"\"user_id\":\"" + user_id + '\"' +
				",\"username\":\"" + username + '\"' +
				",\"jwtToken\":\"" + jwtToken + '\"' +
				'}';
		return finalToken;
	}
}