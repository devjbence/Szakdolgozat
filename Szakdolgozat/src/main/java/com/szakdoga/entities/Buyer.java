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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "buyer")
public class Buyer extends EntityBase {

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "buyer_product_category", joinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Set<ProductCategory> categories;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProductComment> comments;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Lob
	@Column(name = "about_me", length = 1024)
	private String aboutMe;

	@JsonIgnore
	@Lob
	@Column(name = "profile_image")
	private byte[] profileImage;

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

	public Set<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<ProductCategory> categories) {
		this.categories = categories;
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

	public String Username()
	{
		return user.getUsername();
	}

	public List<ProductComment> getComments() {
		return comments;
	}

	public void setComments(List<ProductComment> comments) {
		this.comments = comments;
	}
	
	public void removeComment(ProductComment comment)
	{
		comments.remove(comment);
	}
}
