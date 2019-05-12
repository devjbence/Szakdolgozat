package com.szakdoga.services.interfaces;

import java.util.List;

import com.szakdoga.entities.Role;
import com.szakdoga.entities.User;

public interface ITestDataCreator {
	List<Role> createRoles();
	List<User> createUsers();
}
