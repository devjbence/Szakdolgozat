package com.szakdoga.entities;

import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_activation")
public class UserActivation extends EntityBase{
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="activation_string")
	private String activationString;
	
	@Column(name="expiration_date")
	private Date expiration_date;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getActivationString() {
		return activationString;
	}

	public void setActivationString(String activationString) {
		this.activationString = activationString;
	}

	public Date getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}
	
	
}
