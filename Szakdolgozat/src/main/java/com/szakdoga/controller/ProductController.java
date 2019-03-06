package com.szakdoga.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.DTOs.CommentDTO;
import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.services.interfaces.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping 
	public void create(@RequestBody ProductDTO productDTO)
	{
		productService.addProduct(productDTO);
	}
	
	@DeleteMapping("/{productId}")
	public void deleteOne(@PathVariable("productId") int productId)
	{
		productService.removeProduct(productId);
	}
	
	@DeleteMapping()
	public void deleteAll()
	{
		productService.removeAllProducts();
	}
	
	@PutMapping("/{productId}")
	public void update(@PathVariable("productId") int productId,@RequestBody ProductDTO productDTO)
	{
		productService.updateProduct(productId,productDTO);
	}
	
	@RequestMapping(value = "/addProductImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadImage(
			@RequestPart("productId") String productId,
			@RequestPart("image") MultipartFile imageFile) throws IOException {
		int prodId= Integer.parseInt(productId);
		productService.addImage(prodId, imageFile);
		return new ResponseEntity<>("File is uploaded successfully", HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deleteProductImage/{imageId}")
	public void deleteProductImage(@PathVariable("imageId") Integer imageId)
	{
		productService.removeImage(imageId);
	}
	
	@DeleteMapping("/deleteAllProductImages/{productId}")
	public void deleteAllProductImages(@PathVariable("productId") Integer productId)
	{
		productService.removeAllImages(productId);
	}
	
	@GetMapping("getAllProductImageIds/{productId}")
	public List<Integer> getAllProductImageIds(@PathVariable("productId") Integer productId)
	{
		List<Integer> ids =productService.getAllProductImageIds(productId);
		return ids;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getProductImage/{imageId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@PathVariable("imageId") Integer imageId) {

		return productService.getProductImage(imageId);
	}
}
