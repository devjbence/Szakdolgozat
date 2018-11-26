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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="seller")
public class Seller extends EntityBase{

	@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="seller_product_category",
	joinColumns = @JoinColumn(name="seller_id", referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="category_id",referencedColumnName="id"))
	private Set<ProductCategory> categories;
	
	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Product> products;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Lob 
	@Column(name="about_me", length=1024)
	private String aboutMe;

	@Lob
	@Column(name="profile_image")
	private byte[] profileImage;

	public void addCategory(ProductCategory category)
	{
		if(categories == null)
			categories = new HashSet<ProductCategory>();
		categories.add(category);
	}
	
	public void removeCategory(ProductCategory category)
	{
		if(categories == null)
			return;
		categories.remove(category);
	}
	
	
	
	public Set<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<ProductCategory> categories) {
		this.categories = categories;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

	public void addProduct(Product product)
	{
		if(products==null)
			products = new HashSet<Product>();
		products.add(product);
	}
	public void removeProduct(Product product)
	{
		if(products==null)
			return;
		products.remove(product);
	}
}
