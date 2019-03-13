package com.szakdoga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Bid;

public interface BidRepository extends JpaRepository<Bid, Integer> {
	Bid findById(Integer id);
}
