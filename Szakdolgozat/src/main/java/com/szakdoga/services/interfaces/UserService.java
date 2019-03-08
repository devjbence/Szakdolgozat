package com.szakdoga.services.interfaces;

import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.UserDTO;


public interface UserService extends BaseService<User,UserDTO> {
	void activateUser(String activationCode);
	void checkIfActivated(User user);
	String getCurrentUsername();
	User getCurrentUser();
}
