package com.szakdoga.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.szakdoga.entities.DTOs.BuyerDTO;
import com.szakdoga.services.interfaces.BuyerService;
import com.szakdoga.services.interfaces.UserService;

@RestController("/buyer")
public class BuyerController {

	@Autowired
	private BuyerService buyerService;
	@Autowired
	private UserService userService;
	
	@PutMapping("/{id}")
	public BuyerDTO update(
			@PathVariable("id") int id,
			@RequestBody BuyerDTO dto,
			HttpServletResponse response)
	{
		if (buyerService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return null;
		}
		
		userService.checkIfActivated(userService.getCurrentUser());
		
		return buyerService.update(id,dto);		
	}
	
	@PostMapping(path="/profileImage",consumes = "multipart/form-data")
	public void uploadProfileImage(
			  @RequestPart("image") MultipartFile file)
	{	
		User user = userService.getCurrentUser();
		
		userService.checkIfActivated(user);
		
		buyerService.saveImage(user.getBuyer(),file);
	}
	
	@GetMapping("/{id}")
	public BuyerDTO get(@PathVariable("id") Integer id, HttpServletResponse response) {
		BuyerDTO dto = buyerService.get(id);

		if (dto == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return null;
		}

		return dto;
	}
	
	@GetMapping("/all")
	public List<BuyerDTO> getAll() {
		return buyerService.getAll();
	}
	
	@GetMapping("/size")
	public int size() {
		return buyerService.size();
	}
	
	@GetMapping("/all/{page}/{size}")
	public List<BuyerDTO> getAll(
			@PathVariable("page") Integer page,
			@PathVariable("size") Integer size)
	{
		return buyerService.getAll(page,size);
	}
}

























