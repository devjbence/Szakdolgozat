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
import org.springframework.web.bind.annotation.RestController;

import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.AttributeDTO;
import com.szakdoga.exceptions.UserDoesNotExistsException;
import com.szakdoga.services.interfaces.AttributeService;

@RestController
@RequestMapping("/productFilter")
public class ProductFilterController {

	@Autowired
	private AttributeService attributeService;

	@PostMapping()
	public void create(@RequestBody AttributeDTO dto, HttpServletResponse response) {
		
		response.setStatus(HttpServletResponse.SC_CREATED); 
		
		attributeService.validate(dto);
		
		attributeService.add(dto);
	}

	@GetMapping("/{id}")
	public AttributeDTO get(@PathVariable("id") Integer id, HttpServletResponse response) {
		AttributeDTO dto = attributeService.get(id);

		if (dto == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return null;
		}

		return dto;
	}
	
	@GetMapping("/all")
	public List<AttributeDTO> getAll() {
		return attributeService.getAll();
	}
	
	@GetMapping("/size")
	public int size() {
		return attributeService.size();
	}
	
	@GetMapping("/all/page/{page}/size/{size}")
	public List<AttributeDTO> getAll(
			@PathVariable("page") Integer page,
			@PathVariable("size") Integer size)
	{
		return attributeService.getAll(page,size);
	}

	@PutMapping("/{id}")
	public void update(
			@RequestBody AttributeDTO dto,
			@PathVariable("id") Integer id, 
			HttpServletResponse response)
	{		
		if (attributeService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return;
		}
		
		attributeService.validate(dto);
		
		attributeService.update(id,dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response) {

		if (attributeService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
		}
		
		attributeService.delete(id);
	}
}
