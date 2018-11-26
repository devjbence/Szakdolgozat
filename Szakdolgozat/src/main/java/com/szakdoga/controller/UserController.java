package com.szakdoga.controller;


import java.net.URI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.UserDTO;
import com.szakdoga.exceptions.ActivationExpiredException;
import com.szakdoga.exceptions.MissingUserInformationException;
import com.szakdoga.exceptions.WrongActivationCodeException;
import com.szakdoga.repos.UserRepository;
import com.szakdoga.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepo;

	@GetMapping("/usernameByUserId/{id}")
	public ResponseEntity<String> getUserIdByUsername(@PathVariable("id") Integer id)
	{
		User user = userRepo.findById(id);
		
		if(user==null)
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
			
		return new ResponseEntity<>(user.getUsername(), HttpStatus.OK); 
	}
	
	
	/***
	 * https://stackoverflow.com/questions/24551915/how-to-get-form-data-as-a-map-in-spring-mvc-controller
	 * !Make sure the Content-type is application/x-www-form-urlencoded!
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> register(@RequestBody MultiValueMap<String, String> user) throws Exception {

		String username = user.getFirst("username") == null ? "" : user.getFirst("username");
		String email = user.getFirst("email") == null ? "" : user.getFirst("email");
		String password = user.getFirst("password") == null ? "" : user.getFirst("password");

		if (username.isEmpty() || email.isEmpty() || password.isEmpty())
			throw new MissingUserInformationException("Userinformation is missing");
		
		userService.register(username, email, password);

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
