package com.szakdoga.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
	ProductImage findById(Integer id);
}
