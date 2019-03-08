package com.szakdoga.entities.DTOs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szakdoga.entities.Image;

public class SellerDTO{
	private int id;
	private String username;
	private String firstName;
	private String lastName;
	private String aboutMe;
	private int profileImageId;
	List<Integer> categories;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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

	public int getProfileImage() {
		return profileImageId;
	}
	public void setProfileImage(int profileImage) {
		this.profileImageId = profileImage;
	}
	public List<Integer> getCategories() {
		return categories;
	}
	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}
}
