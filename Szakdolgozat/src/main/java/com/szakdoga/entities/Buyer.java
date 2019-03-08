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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "buyer")
@Getter
@Setter
public class Buyer extends EntityBase {

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "buyer_product_category", joinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Set<ProductCategory> categories;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> comments;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Lob
	@Column(name = "about_me", length = 1024)
	private String aboutMe;

	@OneToOne
	@JoinColumn(name="image_id")
	private Image profileImage;

	public void addCategory(ProductCategory category) {
		if (categories == null)
			categories = new HashSet<ProductCategory>();
		categories.add(category);
	}

	public void removeCategory(ProductCategory category) {
		if (categories == null)
			return;
		categories.remove(category);
	}

	public String Username()
	{
		return user.getUsername();
	}
	
	public void removeComment(Comment comment)
	{
		comments.remove(comment);
	}
}
