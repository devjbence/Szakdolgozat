package com.szakdoga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.AttributeCore;

public interface AttributeCoreRepository extends JpaRepository<AttributeCore, Integer> {
	AttributeCore findById(Integer id);
}
