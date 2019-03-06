package com.szakdoga.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
	Attribute findById(Integer id);
}
