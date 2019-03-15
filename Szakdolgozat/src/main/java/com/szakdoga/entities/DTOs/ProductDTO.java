package com.szakdoga.entities.DTOs;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.szakdoga.enums.ProductType;

import lombok.Data;

@Data
public class ProductDTO {
	private Integer id;
	private String username;
	private String name;
	private String description;
	private Integer seller;
	private Integer buyer;
	@JsonFormat(shape=Shape.STRING, pattern="yyyy-MM-dd HH:mm")
	private Date start;
	@JsonFormat(shape=Shape.STRING, pattern="yyyy-MM-dd HH:mm")
	private Date end;
	private int price;
	private ProductType type;
	private List<Integer> categories;
	private List<Integer> comments;
	private List<Integer> images;
	private List<Integer> attributes;
	private List<Integer> biddings;
	
	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", username=" + username + ", name=" + name + ", description=" + description
				+ ", seller=" + seller + ", buyer=" + buyer + ", start=" + start + ", end=" + end + ", type=" + type
				+ ", price=" + price + ", categories=" + categories + ", comments=" + comments + ", images=" + images
				+ ", attributes=" + attributes + ", biddings=" + biddings + "]";
	}
}
