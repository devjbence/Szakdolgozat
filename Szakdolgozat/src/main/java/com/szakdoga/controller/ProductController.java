package com.szakdoga.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.services.interfaces.ProductService;
import com.szakdoga.services.interfaces.UserService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	
	@PostMapping 
	public void create(@RequestBody ProductDTO dto, HttpServletResponse response)
	{
		userService.checkIfActivated(userService.getCurrentUser());
		
		productService.ValidateDto(dto);
		
		response.setStatus(HttpServletResponse.SC_CREATED); 
		
		productService.add(dto);
	}
	
	@GetMapping("/{id}")
	public ProductDTO get(@PathVariable("id") Integer id, HttpServletResponse response) {
		ProductDTO dto = productService.get(id);

		if (dto == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return null;
		}

		return dto;
	}
	
	@GetMapping("/all")
	public List<ProductDTO> getAll() {
		return productService.getAll();
	}
	
	@GetMapping("/size")
	public int size() {
		return productService.size();
	}

	@PutMapping("/{id}")
	public void update(
			@RequestBody ProductDTO dto,
			@PathVariable("id") Integer id, 
			HttpServletResponse response)
	{		
		if (productService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return;
		}
		
		userService.checkIfActivated(userService.getCurrentUser());
		
		productService.update(id,dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response) {

		if (productService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
		}
		
		productService.delete(id);
	}
	
	@RequestMapping(value = "/image", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadImage(
			@RequestPart("productId") String productId,
			@RequestPart("image") MultipartFile imageFile) throws IOException {
		int prodId= Integer.parseInt(productId);
		productService.addImage(prodId, imageFile);
		return new ResponseEntity<>("File is uploaded successfully", HttpStatus.ACCEPTED);
	}
	
}
