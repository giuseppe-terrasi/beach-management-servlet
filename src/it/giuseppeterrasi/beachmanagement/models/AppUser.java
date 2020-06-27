package it.giuseppeterrasi.beachmanagement.models;

import java.util.ArrayList;
import java.util.List;

public class AppUser {

	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private List<String> roles;
	
	public AppUser() {
		roles = new ArrayList<String>();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
}
