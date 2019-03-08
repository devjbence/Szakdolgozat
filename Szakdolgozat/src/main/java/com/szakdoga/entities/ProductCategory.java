package com.szakdoga.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="product_category")
public class ProductCategory extends EntityBase{

	@ManyToMany(mappedBy = "categories")
	Set<Buyer> buyers;
	
	@ManyToMany(mappedBy = "categories")
	Set<Seller> sellers;
	
	@ManyToMany(mappedBy = "categories")
	Set<Product> jobs;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="parent_id")
	private Integer parentId;
	
	private String about;
	
	@Column(columnDefinition = "TINYINT(1)")
	private boolean active;
}
