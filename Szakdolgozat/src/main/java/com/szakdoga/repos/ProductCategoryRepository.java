package com.szakdoga.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.szakdoga.entities.ProductCategory;

import java.lang.Integer;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	ProductCategory findById(Integer id);
	Page<List<ProductCategory>> findByBuyersIn(@Param("buyers") List<ProductCategory> buyers,Pageable pageable);
	List<ProductCategory> findByIdIn(List<Integer> ids);
}
