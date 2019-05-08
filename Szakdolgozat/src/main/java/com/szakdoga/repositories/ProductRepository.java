package com.szakdoga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Product;

import java.lang.Integer;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findByCategories(Set categories);
	Optional<Product> findById(Integer id);
}
