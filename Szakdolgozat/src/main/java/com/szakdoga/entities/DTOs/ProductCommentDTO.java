package com.szakdoga.entities.DTOs;

import java.util.List;

public class ProductCommentDTO {
	Integer productId;
	Integer productCommentId;
	String username;
	String message;
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
	public Integer getProductCommentId() {
		return productCommentId;
	}
	public void setProductCommentId(Integer productCommentId) {
		this.productCommentId = productCommentId;
	}
	@Override
	public String toString() {
		return "ProductCommentDTO [productId=" + productId + ", productCommentId=" + productCommentId + ", username="
				+ username + ", message=" + message + "]";
	}
	
}
