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

import com.szakdoga.entities.DTOs.AttributeNameDTO;
import com.szakdoga.services.interfaces.AttributeNameService;
import com.szakdoga.services.interfaces.UserService;

@RestController
@RequestMapping("/attributename")
public class AttributeNameController {

	@Autowired
	private AttributeNameService attributeNameService;
	@Autowired
	private UserService userService;

	@PostMapping
	public void create(@RequestBody AttributeNameDTO dto, HttpServletResponse response) {
		
		userService.checkIfActivated(userService.getCurrentUser());
		
		response.setStatus(HttpServletResponse.SC_CREATED);
		
		attributeNameService.add(dto);
	}

	@GetMapping("/{id}")
	public AttributeNameDTO get(@PathVariable("id") Integer id, HttpServletResponse response) {
		AttributeNameDTO dto = attributeNameService.get(id);

		if (dto == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return null;
		}

		return dto;
	}
	
	@GetMapping("/all/{page}/{size}")
	public List<AttributeNameDTO> getAll(
			@PathVariable("page") Integer page,
			@PathVariable("size") Integer size)
	{		
		return attributeNameService.getAll(page,size);
	}
	
	@GetMapping("/all")
	public List<AttributeNameDTO> getAll() {
		return attributeNameService.getAll();
	}
	
	@GetMapping("/size")
	public int size() {
		return attributeNameService.size();
	}

	@PutMapping("/{id}")
	public void update(
			@RequestBody AttributeNameDTO dto,
			@PathVariable("id") Integer id, 
			HttpServletResponse response)
	{		
		if (attributeNameService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return;
		}
		
		userService.checkIfActivated(userService.getCurrentUser());
		
		attributeNameService.update(id,dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response) {

		if (attributeNameService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return;
		}
		
		attributeNameService.delete(id);
	}
}
