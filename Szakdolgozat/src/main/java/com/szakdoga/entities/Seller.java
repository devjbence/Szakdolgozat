package com.szakdoga.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="seller")
public class Seller extends EntityBase{
	
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

	@OneToOne
	@JoinColumn(name="image_id")
	private Image profileImage;

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

	@Override
	public String toString() {
		return "Seller [ products=" + products + ", user=" + user + ", firstName="
				+ firstName + ", lastName=" + lastName + ", aboutMe=" + aboutMe + ", profileImage=" + profileImage
				+ "]";
	}
}
