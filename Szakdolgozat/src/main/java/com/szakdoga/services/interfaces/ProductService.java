package com.szakdoga.services.interfaces;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Product;
import com.szakdoga.entities.DTOs.ProductDTO;

public interface ProductService extends BaseService<Product,ProductDTO>{
	void addImage(Integer productId,MultipartFile imageFile);
	void removeImage(Integer imageId);
	void removeAllImages(Integer productId);
	List<Integer> getAllProductImageIds(Integer productId);
	byte[] getProductImage(Integer imageId);
	void ValidateDto(ProductDTO dto);
}
