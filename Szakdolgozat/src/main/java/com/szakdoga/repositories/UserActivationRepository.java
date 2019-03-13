package com.szakdoga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.UserActivation;

import java.lang.String;

public interface UserActivationRepository extends JpaRepository<UserActivation, Integer> {
	UserActivation findByActivationString(String activationstring);
}
