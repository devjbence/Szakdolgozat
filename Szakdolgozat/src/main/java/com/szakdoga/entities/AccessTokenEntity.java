package com.szakdoga.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="oauth_access_token")
public class AccessTokenEntity {
	@Column(name = "token_id")
	String tokenId;
	byte[] token;
	@Id
	@Column(name = "authentication_id")
	String authenticationId;
	@Column(name = "user_name")
	String username;
	@Column(name = "client_id")
	String clientId;
	byte[] authentication;
	@Column(name = "refresh_token")
	String refreshToken;
}
