package com.szakdoga.services.interfaces;


import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Product;
import com.szakdoga.entities.DTOs.ProductDTO;

public interface ProductService extends BaseService<Product,ProductDTO>{
	void addImage(Integer productId,MultipartFile imageFile);
	void saveImage(Integer id, MultipartFile file);
	void removeImage(Integer entityId, Integer imageId);
	void buy(Integer id);
}
