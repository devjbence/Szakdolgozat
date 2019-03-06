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

import com.szakdoga.entities.DTOs.CommentDTO;
import com.szakdoga.services.interfaces.CommentService;
import com.szakdoga.services.interfaces.UserService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;

	@PostMapping("/")
	public void create(@RequestBody CommentDTO dto, HttpServletResponse response) {
		
		userService.checkIfActivated(userService.getCurrentUser());
		
		commentService.ValidateDto(dto);
		
		response.setStatus(HttpServletResponse.SC_CREATED);
		
		commentService.add(dto);
	}

	@GetMapping("/{id}")
	public CommentDTO get(@PathVariable("id") Integer id, HttpServletResponse response) {
		CommentDTO dto = commentService.get(id);

		if (dto == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return null;
		}

		return dto;
	}
	
	@GetMapping("/all")
	public List<CommentDTO> getAll() {
		return commentService.getAll();
	}
	
	@GetMapping("/size")
	public int size() {
		return commentService.size();
	}

	@PutMapping("/{id}")
	public void update(
			@RequestBody CommentDTO dto,
			@PathVariable("id") Integer id, 
			HttpServletResponse response)
	{		
		if (commentService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return;
		}
		
		userService.checkIfActivated(userService.getCurrentUser());
		
		commentService.update(id,dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response) {

		if (commentService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
		}
		
		commentService.delete(id);
	}
}