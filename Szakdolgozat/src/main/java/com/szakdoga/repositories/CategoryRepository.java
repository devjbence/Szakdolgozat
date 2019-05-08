package com.szakdoga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Category;

import java.lang.Integer;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Optional<Category> findById(Integer id);
	List<Category> findByIdIn(List<Integer> ids);
}
