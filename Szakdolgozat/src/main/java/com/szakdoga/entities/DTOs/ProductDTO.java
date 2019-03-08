package com.szakdoga.entities.DTOs;

import java.util.List;

import lombok.Data;

@Data
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
}
