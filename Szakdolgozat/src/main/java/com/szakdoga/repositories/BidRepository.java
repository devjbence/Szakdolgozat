package com.szakdoga.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Bid;

public interface BidRepository extends JpaRepository<Bid, Integer> {
	Optional<Bid> findById(Integer id);
}
