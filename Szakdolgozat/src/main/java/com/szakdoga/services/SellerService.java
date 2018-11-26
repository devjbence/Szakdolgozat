package com.szakdoga.services;

import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.DTOs.SellerDTO;

public interface SellerService {
	void updateProfile(SellerDTO offererDTO);
	void saveImage(String username, MultipartFile imageFile);
	 byte[] getProfileImage(String username);
}
