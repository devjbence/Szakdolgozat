package com.szakdoga.services;

import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.DTOs.BuyerDTO;

public interface BuyerService {
	/***
	 * Updates the profile with the given DTO.
	 * The DTO must contain a username.
	 * Everything else in it is optional, such as categories, aboutMe,..etc
	 * @param buyerDTO
	 */
	void updateProfile(BuyerDTO buyerDTO);
	
	/**
	 * Saves the image to the given user, who is referenced by the username.
	 * If the file size exceeds to MB-s, then org.springframework.web.multipart.MultipartException happens.
	 * @param username
	 * @param imageFile
	 */
	void saveImage(String username, MultipartFile imageFile);
	
	byte[] getProfileImage(String username);
}
