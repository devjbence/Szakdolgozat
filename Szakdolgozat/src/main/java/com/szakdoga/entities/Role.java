package com.szakdoga.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import org.springframework.security.core.GrantedAuthority;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends EntityBase implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	@ManyToMany(mappedBy = "roles")
	Set<User> users;

	@Override
	public String getAuthority() {
		return name;
	}
}
