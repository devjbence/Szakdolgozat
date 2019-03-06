package com.szakdoga.services.interfaces;

import java.security.Principal;
import java.util.List;

import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.UserDTO;


public interface UserService {

	List<User> findAll();
	void register(UserDTO userDTO);
	void activateUser(String activationCode);
	void checkIfActivated(User user);
	User checkUserValues(String username);
	void removeUser(String username);
	void removeAllUsers();
	void changePassword(UserDTO userDTO);
	String getCurrentUsername();
	User getCurrentUser();
}
