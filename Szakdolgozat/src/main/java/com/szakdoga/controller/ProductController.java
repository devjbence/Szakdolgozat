package com.szakdoga.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.CategoryDTO;
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
	public ProductDTO create(@RequestBody ProductDTO dto, HttpServletResponse response) {
		userService.checkIfActivated(userService.getCurrentUser());

		response.setStatus(HttpServletResponse.SC_CREATED);
		
		productService.validate(dto);

		return productService.add(dto);
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
	
	@GetMapping("/all/{page}/{size}")
	public List<ProductDTO> getAll(
			@PathVariable("page") Integer page,
			@PathVariable("size") Integer size)
	{		
		return productService.getAll(page,size);
	}

	@GetMapping("/all")
	public List<ProductDTO> getAll() {
		return productService.getAll();
	}
	
	///get all categories
	@GetMapping("/categories")
	public List<CategoryDTO> getAllCategories() {
		return productService.getAllCategories();
	}

	@GetMapping("/size")
	public int size() {
		return productService.size();
	}

	@PutMapping("/{id}")
	public ProductDTO update(@RequestBody ProductDTO dto, @PathVariable("id") Integer id, HttpServletResponse response) {
		if (productService.get(id) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		userService.checkIfActivated(userService.getCurrentUser());

		return productService.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response) {

		if (productService.get(id) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		productService.delete(id);
	}

	@PostMapping(path = "/image/{id}", consumes = "multipart/form-data")
	public int uploadImage(@PathVariable("id") Integer id, @RequestPart("image") MultipartFile file,
			HttpServletResponse response) {
		User user = userService.getCurrentUser();

		userService.checkIfActivated(user);

		ProductDTO dto = productService.get(id);

		if (dto == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return 0;
		}
		
		if (dto.getSeller() != user.getSeller().getId()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

		return productService.addImage(id, file);
	}
	
	@DeleteMapping("/image/{entityId}/{imageId}")
	public void deleteImage(
			@PathVariable("entityId") int entityId,
			@PathVariable("imageId") int imageId,
			HttpServletResponse response) 
	{
		User user = userService.getCurrentUser();

		userService.checkIfActivated(user);
		
		ProductDTO dto = productService.get(entityId);

		if (dto == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		if (!dto.getImages().contains(imageId)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		productService.removeImage(entityId, imageId);
	}
	
	@PostMapping("/bid/{entityId}/{price}")
	public void placeBid(
			@PathVariable("entityId") int entityId,
			@PathVariable("price") int price,
			HttpServletResponse response)
	{
		User user = userService.getCurrentUser();

		userService.checkIfActivated(user);
		
		if(productService.get(entityId) == null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		productService.bid(entityId,price);
	}
	
	@PostMapping("/buy/{id}")
	public void buy(
			@PathVariable("id") Integer id,
			HttpServletResponse response)
	{
		User user = userService.getCurrentUser();

		userService.checkIfActivated(user);
		
		if(productService.get(id) == null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		productService.buy(id);
	}
}
