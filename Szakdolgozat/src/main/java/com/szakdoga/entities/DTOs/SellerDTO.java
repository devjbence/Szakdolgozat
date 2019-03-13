package com.szakdoga.entities.DTOs;


import lombok.Data;

@Data
public class SellerDTO{
	private int id;
	private String username;
	private String firstName;
	private String lastName;
	private String aboutMe;
	private int profileImage;
}
