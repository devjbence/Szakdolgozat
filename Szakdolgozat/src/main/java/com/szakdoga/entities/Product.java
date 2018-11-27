package com.szakdoga.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Product extends EntityBase {
	String name;
	@Lob
	@Column(name = "description", length = 2048)
	private String description;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) // persist https://stackoverflow.com/a/48421327
	@JoinTable(name = "product_product_category", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Set<ProductCategory> categories;

	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ProductImage> images;

	
	public void addImage(ProductImage image)
	{
		if(images == null)
			images = new HashSet<ProductImage>();
		images.add(image);
	}
	
	public void removeImage(ProductImage image)
	{
		if(images == null)
			return;
		images.remove(image);
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

	public Set<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<ProductCategory> categories) {
		this.categories = categories;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	// https://stackoverflow.com/a/48421327
	public void addCategories(ProductCategory category) {
		if(category == null)
			return;
		if (categories == null)
			categories = new HashSet<ProductCategory>();
		if (!categories.contains(category)) {
			categories.add(category);
			category.getJobs().add(this);
		}
	}

	// https://stackoverflow.com/a/48421327
	public void removeCategories(ProductCategory category) {
		if (categories == null)
			return;
		categories.remove(category);
		category.getJobs().remove(this);
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}
	
	
}
