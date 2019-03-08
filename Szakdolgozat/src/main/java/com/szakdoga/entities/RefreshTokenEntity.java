package com.szakdoga.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class RefreshTokenEntity {
	@Id
	@Column(name = "token_id")
	String tokenId;
	byte[] token;
	byte[] authentication;	
}
