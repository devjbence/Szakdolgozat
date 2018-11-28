package com.szakdoga.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.ProductComment;
import com.szakdoga.entities.User;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Integer> {

	ProductComment findById(Integer id);
}
