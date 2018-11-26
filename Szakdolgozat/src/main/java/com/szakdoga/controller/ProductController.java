package com.szakdoga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.services.ProductService;

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
	
	@DeleteMapping("/user/{username}")
	public void deleteAll(@PathVariable("username") String username)
	{
		productService.removeAllProducts(username);
	}
	
	@PutMapping("/{productId}")
	public void update(@PathVariable("productId") int productId,@RequestBody ProductDTO productDTO)
	{
		productService.updateProduct(productId,productDTO);
	}
	
}
