package com.szakdoga.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.szakdoga.enums.ProductType;

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
	@JoinTable(name = "product_category", 
			   joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), 
			   inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Set<Category> categories;

	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Attribute> attributes;
	
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(
            name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
            )
	private Set<Image> images;
	
	@Enumerated(EnumType.ORDINAL)
	private ProductType type;
  
	private Date end;
	
	//bindding
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Bid> biddings;

	//fixed price
	private Integer price;
	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean active;
	
	public void addBid(Bid bid)
	{
		if(biddings == null)
			biddings = new ArrayList<Bid>();
		biddings.add(bid);
	}
	
	public void removeBid(Bid bid)
	{
		if(biddings == null)
			return;
		biddings.remove(bid);
	}
    
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

	public void addAttribute(Attribute attribute)
	{
		if(attributes == null)
			attributes = new ArrayList<Attribute>();
		attributes.add(attribute);
	}
	
	public void removeAttribute(Attribute attribute)
	{
		if(attributes == null)
			return;
		attributes.remove(attribute);
	}
	
	// https://stackoverflow.com/a/48421327
	public void addCategory(Category category) {
		if(category == null)
			return;
		if (categories == null)
			categories = new HashSet<Category>();
		if (!categories.contains(category)) {
			categories.add(category);
			category.getProducts().add(this);
		}
	}

	// https://stackoverflow.com/a/48421327
	public void removeCategory(Category category) {
		if (categories == null)
			return;
		categories.remove(category);
		category.getProducts().remove(this);
	}
	
	public void addComment(Comment comment)
	{
		if(comment == null)
			comments = new ArrayList<Comment>();
		comments.add(comment);
	}
	
	public void removeComment(Comment comment)
	{
		comments.remove(comment);
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", description=" + description + ", categories=" + categories + ", seller="
				+ seller + ", comments=" + comments + ", attributes=" + attributes + ", images=" + images + ", id=" + id
				+ ", created=" + created + ", modified=" + modified + ", getName()=" + getName() + ", getDescription()="
				+ getDescription() + ", getCategories()=" + getCategories() + ", getSeller()=" + getSeller()
				+ ", getComments()=" + getComments() + ", getAttributes()=" + getAttributes() + ", getImages()="
				+ getImages() + ", getId()=" + getId() + ", getCreated()=" + getCreated() + ", getModified()="
				+ getModified() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
