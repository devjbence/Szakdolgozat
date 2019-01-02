package com.szakdoga.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.ProductComment;
import com.szakdoga.entities.User;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Integer> {

	ProductComment findById(Integer id);
	
	@Query("select c from ProductComment c join c.buyer bu join bu.user us where us.username = :username")
	List<ProductComment> getCommentsByUsername(String username);
	
	@Query("select c from ProductComment c join c.product pro where pro.id = :productId")
	List<ProductComment> getCommentsByProductId(Integer productId);
}
