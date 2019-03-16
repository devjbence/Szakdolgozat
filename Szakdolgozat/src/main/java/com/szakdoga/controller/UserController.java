package com.szakdoga.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szakdoga.entities.DTOs.UserDTO;
import com.szakdoga.exceptions.ActivationExpiredException;
import com.szakdoga.exceptions.WrongActivationCodeException;
import com.szakdoga.services.implementations.DbPollService;
import com.szakdoga.services.interfaces.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private DbPollService poll;
	
	private boolean once = true;
	
	@GetMapping("/poll")
	public void startPoll()
	{
		if(once)
		{
			poll.start(5);
			System.out.println("Poll has started");
			once=false;
		}
	}
	
	@PostMapping
	public UserDTO create(@RequestBody UserDTO dto, HttpServletResponse response)
	{		
		response.setStatus(HttpServletResponse.SC_CREATED); 
		
		return userService.add(dto);
	}
	
	@GetMapping("/activation/{activationCode}")
	public void userActivation(@PathVariable("activationCode") String activationCode,HttpServletResponse response) 
	{
		String url="";
		
		try {
			userService.activateUser(activationCode);
		}catch(ActivationExpiredException ex)
		{
			url="https://en.wikipedia.org/wiki/Expiration";
			
			response.setHeader("Location", url);
			response.setStatus(302);
			return;
		}
		catch(WrongActivationCodeException ex)
		{
			url="https://www.thesaurus.com/noresult?term=wrong%20activation%20code&s=ts";
			
			response.setHeader("Location", url);
			response.setStatus(302);
			return;
		}

		url="https://en.m.wikipedia.org/wiki/Success\"";
		
		response.setHeader("Location", url);
		response.setStatus(302);
	}
	
	@PutMapping("/{id}")
	public UserDTO update(
			@PathVariable("id") int id,
			@RequestBody UserDTO dto,
			HttpServletResponse response)
	{
		if (userService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return null;
		}
		
		return userService.update(id,dto);		
	}
	
	@GetMapping("/{id}")
	public UserDTO get(@PathVariable("id") Integer id, HttpServletResponse response) {
		UserDTO dto = userService.get(id);

		if (dto == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return null;
		}

		return dto;
	}
	
	@GetMapping("/all")
	public List<UserDTO> getAll() {
		return userService.getAll();
	}
	
	@GetMapping("/size")
	public int size() {
		return userService.size();
	}
	
	@GetMapping("/all/{page}/{size}")
	public List<UserDTO> getAll(
			@PathVariable("page") Integer page,
			@PathVariable("size") Integer size)
	{
		return userService.getAll(page,size);
	}
}
