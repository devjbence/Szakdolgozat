package com.szakdoga.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user_activation")
public class UserActivation extends EntityBase{
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="activation_string")
	private String activationString;
	
	@Column(name="expiration_date")
	private Date expiration_date;
}
