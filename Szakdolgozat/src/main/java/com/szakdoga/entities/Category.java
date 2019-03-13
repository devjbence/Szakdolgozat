package com.szakdoga.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category extends EntityBase{
	
	@ManyToMany(mappedBy = "categories")
	Set<Product> products;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="parent_id")
	private Integer parentId;
	
	private String about;
	
	@Column(columnDefinition = "TINYINT(1)")
	private boolean active;
}
