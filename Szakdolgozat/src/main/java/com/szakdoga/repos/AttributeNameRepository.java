package com.szakdoga.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.AttributeName;

public interface AttributeNameRepository extends JpaRepository<AttributeName, Integer> {
	AttributeName findById(Integer id);
}
