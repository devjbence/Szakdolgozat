package com.szakdoga.services.interfaces;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Product;
import com.szakdoga.entities.DTOs.ProductDTO;

public interface ProductService extends BaseService<Product,ProductDTO>{
	void addImage(Integer productId,MultipartFile imageFile);
	byte[] getImage(Integer imageId);
	void ValidateDto(ProductDTO dto);
}
