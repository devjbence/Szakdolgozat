package com.szakdoga.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Seller;
import com.szakdoga.entities.User;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

	Seller findByUser(User user);
	Seller findById(Integer id);
}
