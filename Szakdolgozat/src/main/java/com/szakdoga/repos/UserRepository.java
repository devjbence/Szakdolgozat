package com.szakdoga.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.User;

import java.lang.Integer;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String name);
	User findByEmail(String email);
	User findById(Integer id);
}
