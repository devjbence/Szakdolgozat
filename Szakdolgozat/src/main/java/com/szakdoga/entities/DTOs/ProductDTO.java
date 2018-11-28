package com.szakdoga.entities.DTOs;

import java.util.List;

public class ProductDTO {
	Integer productId;
	String username;
	String name;
	String description;
	
	Boolean fixedPrice;
	Boolean bidding;
	
	List<Integer> categories;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Integer> getCategories() {
		return categories;
	}
	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}
	public Boolean getFixedPrice() {
		return fixedPrice;
	}
	public void setFixedPrice(Boolean fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
	public Boolean getBidding() {
		return bidding;
	}
	public void setBidding(Boolean bidding) {
		this.bidding = bidding;
	}
	
	
}
