package com.szakdoga.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.Comment;
import com.szakdoga.entities.User;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	Optional<Comment> findById(Integer id);
	
	@Query("select c from Comment c join c.buyer bu join bu.user us where us.username = :username")
	List<Comment> getCommentsByUsername(String username);
	
	@Query("select c from Comment c join c.product pro where pro.id = :productId")
	List<Comment> getCommentsByProductId(Integer productId);
}
