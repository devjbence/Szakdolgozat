package com.szakdoga.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.AttributeCore;

public interface AttributeCoreRepository extends JpaRepository<AttributeCore, Integer> {
	Optional<AttributeCore> findById(Integer id);
}
