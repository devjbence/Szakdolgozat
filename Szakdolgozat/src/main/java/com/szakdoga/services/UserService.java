package com.szakdoga.services;

import java.util.List;

import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.UserDTO;


public interface UserService {

	List<User> findAll();
	void register(String username,String email,String password);
	void activateUser(String activationCode);
	void checkIfActivated(User user);
	User checkUserValues(String username);
	void removeUser(String username);
	void removeAllUsers();
	void createAdmin(String username,String email,String password);
	void changePassword(UserDTO userDTO);
}
