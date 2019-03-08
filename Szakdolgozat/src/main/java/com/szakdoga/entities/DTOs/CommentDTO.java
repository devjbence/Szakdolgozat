package com.szakdoga.entities.DTOs;

import lombok.Data;

@Data
public class CommentDTO {
	private Integer id;
	private Integer productId;
	private Integer buyerId;
	private String username;
	private String message;
	
	@Override
	public String toString() {
		return "ProductCommentDTO [productId=" + productId + ", productCommentId=" + id + ", username="
				+ username + ", message=" + message + "]";
	}	
}
