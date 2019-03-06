package com.szakdoga.entities.DTOs;

import java.util.List;

public class ProductDTO {
	private Integer id;
	private String username;
	private String name;
	private String description;
	private Integer sellerId;
	private List<Integer> categories;
	private List<Integer> comments;
	private List<Integer> images;
	
	Boolean fixedPrice;
	Boolean bidding;
	
	
	public List<Integer> getImages() {
		return images;
	}
	public void setImages(List<Integer> images) {
		this.images = images;
	}
	public List<Integer> getComments() {
		return comments;
	}
	public void setComments(List<Integer> comments) {
		this.comments = comments;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return id;
	}
	public void setProductId(Integer productId) {
		this.id = productId;
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
