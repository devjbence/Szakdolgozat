
package com.szakdoga.entities.DTOs;

import lombok.Data;

@Data
public class UserDTO {
	private String username;
	private String password;
	private String newPassword;
	private String email;
	private String role;
}
