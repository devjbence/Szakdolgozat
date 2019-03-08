package com.szakdoga.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends EntityBase{

	private String username;
	private String email;
	@JsonIgnore
	private String password;

	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="user_role",
	joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="role_id",referencedColumnName="id"))
	private Set<Role> roles;
	
	@OneToOne(mappedBy="user")
	private Seller seller;

	@OneToOne(mappedBy="user")
	private Buyer buyer;
	
	@Column(columnDefinition = "TINYINT(1)")
	private boolean activated;
	
	public boolean isActivated() {
		return activated;
	}

	public void addRole(Role role)
	{
		if(roles == null)
			roles = new HashSet<Role>();
		roles.add(role);
	}
	
	public Integer getResourceId()
	{
		return id;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + username + ", password=" + password + ", roles=" + roles + "]";
	}
}
