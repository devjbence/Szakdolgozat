package com.szakdoga.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
	Optional<Attribute> findById(Integer id);
}
