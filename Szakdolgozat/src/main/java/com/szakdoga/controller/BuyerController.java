package com.szakdoga.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.BuyerDTO;
import com.szakdoga.services.BuyerService;
import com.szakdoga.services.UserService;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

	@Autowired
	private BuyerService buyerService;
	@Autowired
	private UserService userService;
	
	@PutMapping("/{id}")
	public void update(
			@PathVariable("id") int id,
			@RequestBody BuyerDTO buyerDTO,
			HttpServletResponse response)
	{
		BuyerDTO dto = buyerService.get(id);

		if (dto == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return;
		}
		
		userService.checkIfActivated(userService.getCurrentUser());
		
		buyerService.update(id,buyerDTO);		
	}
	
	@PostMapping(path="/profileImage",consumes = "multipart/form-data")
	public void uploadProfileImage(
			  @RequestPart("image") MultipartFile file)
	{	
		User user = userService.getCurrentUser();
		
		userService.checkIfActivated(user);
		
		buyerService.saveImage(user.getBuyer(),file);
	}
	
	@ResponseBody
	@GetMapping(path="profileImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@PathVariable("id") int id) {

		return buyerService.getProfileImage(id);
	}
}

























