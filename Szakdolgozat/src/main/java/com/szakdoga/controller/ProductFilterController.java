package com.szakdoga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.szakdoga.entities.DTOs.ProductDTO;
import com.szakdoga.entities.DTOs.ProductFilterDTO;
import com.szakdoga.services.interfaces.ProductFilterService;

@RequestMapping("/productfilter")
@RestController()
public class ProductFilterController {

	@Autowired
	private ProductFilterService productFilterService;

	@PostMapping("/all")
	public List<ProductDTO> getAll(@RequestBody ProductFilterDTO dto) {
		
		productFilterService.validate(dto);
		
		return productFilterService.getAll(dto);
	}
	
	@PostMapping("/all/{page}/{size}")
	public List<ProductDTO> getAll(
			@PathVariable("page") Integer page,
			@PathVariable("size") Integer size,
			@RequestBody ProductFilterDTO dto)
	{
		productFilterService.validate(dto);
		
		return productFilterService.getAll(dto,page,size);
	}
	
	@PostMapping("/size")
	public int size(@RequestBody ProductFilterDTO dto) {
		productFilterService.validate(dto);
		
		return productFilterService.size(dto);
	}
}
