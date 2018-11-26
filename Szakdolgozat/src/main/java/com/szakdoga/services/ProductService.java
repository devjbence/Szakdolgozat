package com.szakdoga.services;


import com.szakdoga.entities.Seller;
import com.szakdoga.entities.DTOs.ProductDTO;

public interface ProductService {
	void addProduct(ProductDTO productDTO);
	void removeProduct(Integer productId);
	void updateProduct(int productId,ProductDTO productDTO);
	void removeAllProducts(Seller seller) ;
	void removeAllProducts(String username) ;
}
