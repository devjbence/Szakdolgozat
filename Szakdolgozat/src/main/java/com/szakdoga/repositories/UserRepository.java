package com.szakdoga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.User;

import java.lang.Integer;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String name);
	User findByEmail(String email);
	Optional<User> findById(Integer id);
}
