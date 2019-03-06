package com.szakdoga.entities.DTOs;

import java.util.List;

public class CommentDTO {
	private Integer id;
	private Integer productId;
	private Integer buyerId;
	private String username;
	private String message;
	
	
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ProductCommentDTO [productId=" + productId + ", productCommentId=" + id + ", username="
				+ username + ", message=" + message + "]";
	}
	
}
