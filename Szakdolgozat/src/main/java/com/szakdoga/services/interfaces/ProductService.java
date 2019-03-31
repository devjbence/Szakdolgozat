package com.szakdoga.services.interfaces;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Product;
import com.szakdoga.entities.DTOs.CategoryDTO;
import com.szakdoga.entities.DTOs.ProductDTO;

public interface ProductService extends BaseService<Product,ProductDTO>{
	int addImage(Integer productId,MultipartFile imageFile);
	void saveImage(Integer id, MultipartFile file);
	void removeImage(Integer entityId, Integer imageId);
	void buy(Integer id);
	void bid(int entityId, int price);
	List<CategoryDTO> getAllCategories();
}
