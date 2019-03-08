
package com.szakdoga.entities.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class UserDTO {
	private int id;
	private String username;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String newPassword;
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String role;
	
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", password=" + password + ", newPassword="
				+ newPassword + ", email=" + email + ", role=" + role + "]";
	}
}
