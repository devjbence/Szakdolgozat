package com.szakdoga.services;

import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.DTOs.BuyerDTO;

public interface BuyerService extends BaseService<Buyer,BuyerDTO>{
	
	void saveImage(Buyer buyer, MultipartFile imageFile);
	
	byte[] getProfileImage(int id);
}
