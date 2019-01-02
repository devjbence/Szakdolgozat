package com.szakdoga.controller;


import java.net.URI;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.UserDTO;
import com.szakdoga.exceptions.ActivationExpiredException;
import com.szakdoga.exceptions.WrongActivationCodeException;
import com.szakdoga.repos.UserRepository;
import com.szakdoga.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepo;

	@GetMapping("/usernameByUserId/{id}")
	public ResponseEntity<String> getUserIdByUsername(@PathVariable("id") Integer id)
	{
		User user = userRepo.findById(id);
		
		if(user==null)
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
			
		return new ResponseEntity<>(user.getUsername(), HttpStatus.OK); 
	}
	
	
	@PostMapping(value = "/register")
	public ResponseEntity<String> register(@RequestBody UserDTO userDTO) throws Exception {
		
		userService.register(userDTO);

		return new ResponseEntity<>("Registered", HttpStatus.CREATED);
	}
	
	@RequestMapping("/registered/activation/{activationCode}")
	public ResponseEntity<Object> userActivation(@PathVariable("activationCode") String activationCode) 
	{
		HttpHeaders headers = new HttpHeaders();
		
		try {
			userService.activateUser(activationCode);
		}catch(ActivationExpiredException ex)
		{
			//TODO: forward to page where it says that you have to register again because the activation has expired
			headers.setLocation(URI.create("https://en.wikipedia.org/wiki/Expiration"));
			return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
			//https://stackoverflow.com/a/47411493
		}
		catch(WrongActivationCodeException ex)
		{
			//TODO: forward to page where it says that you have to register again because the activation has expired
			headers.setLocation(URI.create("https://www.thesaurus.com/noresult?term=wrong%20activation%20code&s=ts"));
			return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
			//https://stackoverflow.com/a/47411493
		}
		//TODO redirect to login page
		headers.setLocation(URI.create("https://en.m.wikipedia.org/wiki/Success"));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, consumes =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changePassword(@RequestBody UserDTO userDTO) throws Exception {
		
		userService.changePassword(userDTO);
		
		return new ResponseEntity<>("Password changed", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{username}")
	public void deleteOne(@PathVariable("username") String username)
	{
		userService.removeUser(username);
	}
	
	@DeleteMapping
	public void deleteAll()
	{
		userService.removeAllUsers();
	}
}
