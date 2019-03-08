package com.szakdoga.entities;

import java.util.HashSet;
import java.util.List;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
	private List<Comment> comments;
	
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(
            name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
            )
	private Set<Image> images;

	
	public void addImage(Image image)
	{
		if(images == null)
			images = new HashSet<Image>();
		images.add(image);
	}
	
	public void removeImage(Image image)
	{
		if(images == null)
			return;
		images.remove(image);
	}

	// https://stackoverflow.com/a/48421327
	public void addCategory(ProductCategory category) {
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
	public void removeCategory(ProductCategory category) {
		if (categories == null)
			return;
		categories.remove(category);
		category.getJobs().remove(this);
	}
	
	public void removeComment(Comment comment)
	{
		comments.remove(comment);
	}
}
