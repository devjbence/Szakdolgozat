package com.szakdoga.services;

import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Seller;
import com.szakdoga.entities.DTOs.SellerDTO;

public interface SellerService extends BaseService<Seller,SellerDTO>{
	void saveImage(Seller entity, MultipartFile imageFile);
	byte[] getProfileImage(int id);
}
