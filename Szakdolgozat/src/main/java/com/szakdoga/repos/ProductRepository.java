package com.szakdoga.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Product;

import java.lang.Integer;
import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findByCategories(Set categories);
	Product findById(Integer id);
}
