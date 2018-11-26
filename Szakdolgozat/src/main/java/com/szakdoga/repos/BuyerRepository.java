package com.szakdoga.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.User;

public interface BuyerRepository extends JpaRepository<Buyer, Integer> {

	Buyer findByUser(User user);
}
