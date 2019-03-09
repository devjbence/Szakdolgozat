package com.szakdoga.entities.DTOs;

import java.util.List;

import lombok.Data;

@Data
public class ProductDTO {
	private Integer id;
	private String username;
	private String name;
	private String description;
	private Integer seller;
	private List<Integer> categories;
	private List<Integer> comments;
	private List<Integer> images;
	private List<Integer> attributes;
	
	Boolean fixedPrice;
	Boolean bidding;
	
	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", username=" + username + ", name=" + name + ", description=" + description
				+ ", seller=" + seller + ", categories=" + categories + ", comments=" + comments + ", images=" + images
				+ ", fixedPrice=" + fixedPrice + ", bidding=" + bidding + "]";
	}
}
