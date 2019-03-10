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

import com.szakdoga.entities.DTOs.AttributeCoreDTO;
import com.szakdoga.services.interfaces.AttributeCoreService;
import com.szakdoga.services.interfaces.UserService;

@RestController
@RequestMapping("/attributecore")
public class AttributeCoreController {

	@Autowired
	private AttributeCoreService attributeCoreService;
	@Autowired
	private UserService userService;

	@PostMapping
	public void create(@RequestBody AttributeCoreDTO dto, HttpServletResponse response) {
		
		userService.checkIfActivated(userService.getCurrentUser());
		
		response.setStatus(HttpServletResponse.SC_CREATED);
		
		attributeCoreService.add(dto);
	}

	@GetMapping("/{id}")
	public AttributeCoreDTO get(@PathVariable("id") Integer id, HttpServletResponse response) {
		AttributeCoreDTO dto = attributeCoreService.get(id);

		if (dto == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return null;
		}

		return dto;
	}
	
	@GetMapping("/all/{page}/{size}")
	public List<AttributeCoreDTO> getAll(
			@PathVariable("page") Integer page,
			@PathVariable("size") Integer size)
	{		
		return attributeCoreService.getAll(page,size);
	}
	
	@GetMapping("/all")
	public List<AttributeCoreDTO> getAll() {
		return attributeCoreService.getAll();
	}
	
	@GetMapping("/size")
	public int size() {
		return attributeCoreService.size();
	}

	@PutMapping("/{id}")
	public void update(
			@RequestBody AttributeCoreDTO dto,
			@PathVariable("id") Integer id, 
			HttpServletResponse response)
	{		
		if (attributeCoreService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return;
		}
		
		userService.checkIfActivated(userService.getCurrentUser());
		
		attributeCoreService.update(id,dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response) {

		if (attributeCoreService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return;
		}
		
		attributeCoreService.delete(id);
	}
}
