package com.szakdoga.entities;

import java.util.List;

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
@Table(name = "buyer")
@Getter
@Setter
public class Buyer extends EntityBase {

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
	
	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Bid> biddings;

	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> products;
	
	public String Username()
	{
		return user.getUsername();
	}
	
	public void removeComment(Comment comment)
	{
		comments.remove(comment);
	}
}
