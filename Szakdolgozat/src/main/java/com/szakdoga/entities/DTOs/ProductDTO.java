package com.szakdoga.entities.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)  
	@JsonSerialize(using = LocalDateTimeSerializer.class) 
	private LocalDateTime end;
	private Integer price;
	private ProductType type;
	private Boolean active;
	private Boolean isOwn;
	private List<Integer> categories;
	private List<Integer> comments;
	private List<Integer> images;
	private List<Integer> attributes;
	private List<Integer> biddings;
	private Integer lastBid;
	@JsonFormat(shape=Shape.STRING, pattern="yyyy-MM-dd HH:mm")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)  
	@JsonSerialize(using = LocalDateTimeSerializer.class) 
	private LocalDateTime lastBidDateTime;
	
	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", username=" + username + ", name=" + name + ", description=" + description
				+ ", seller=" + seller + ", buyer=" + buyer + ", end=" + end + ", type=" + type
				+ ", price=" + price + ", categories=" + categories + ", comments=" + comments + ", images=" + images
				+ ", attributes=" + attributes + ", biddings=" + biddings + "]";
	}
}
