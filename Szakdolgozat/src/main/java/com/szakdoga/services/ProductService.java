package com.szakdoga.services;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.Seller;
import com.szakdoga.entities.DTOs.ProductCommentDTO;
import com.szakdoga.entities.DTOs.ProductDTO;

public interface ProductService {
	void addProduct(ProductDTO productDTO);
	void removeProduct(Integer productId);
	void updateProduct(int productId,ProductDTO productDTO);
	void removeAllProducts(Seller seller) ;
	void removeAllProducts(String username) ;
	void addImage(Integer productId,MultipartFile imageFile);
	void removeImage(Integer imageId);
	void removeAllImages(Integer productId);
	List<Integer> getAllProductImageIds(Integer productId);
	byte[] getProductImage(Integer imageId);
	void createComment(ProductCommentDTO commentDTO); 
	void updateComment(ProductCommentDTO commentDTO); 
	void deleteComment(Integer productCommentId);
	List<ProductCommentDTO> getComments(Integer productId);
}
