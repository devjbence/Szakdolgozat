package com.szakdoga.controller;

import java.util.List;

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
import com.szakdoga.entities.DTOs.SellerDTO;
import com.szakdoga.services.SellerService;
import com.szakdoga.services.UserService;

@RestController
@RequestMapping("/seller")
public class SellerController {
	@Autowired
	private SellerService sellerService;
	@Autowired
	private UserService userService;
	
	@PutMapping("/{id}")
	public void update(
			@PathVariable("id") int id,
			@RequestBody SellerDTO dto,
			HttpServletResponse response)
	{
		if (sellerService.get(id) == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return;
		}
		
		userService.checkIfActivated(userService.getCurrentUser());
		
		sellerService.update(id,dto);		
	}
	
	@PostMapping(path="/profileImage",consumes = "multipart/form-data")
	public void uploadProfileImage(
			  @RequestPart("image") MultipartFile file)
	{	
		User user = userService.getCurrentUser();
		
		userService.checkIfActivated(user);
		
		sellerService.saveImage(user.getSeller(),file);
	}
	
	@ResponseBody
	@GetMapping(path="profileImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@PathVariable("id") int id) {

		return sellerService.getProfileImage(id);
	}
	
	@GetMapping("/{id}")
	public SellerDTO get(@PathVariable("id") Integer id, HttpServletResponse response) {
		SellerDTO dto = sellerService.get(id);

		if (dto == null) {		
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			return null;
		}

		return dto;
	}
	
	@GetMapping("/all")
	public List<SellerDTO> getAll() {
		return sellerService.getAll();
	}
	
	@GetMapping("/size")
	public int size() {
		return sellerService.size();
	}
	
	@GetMapping("/all/page/{page}/size/{size}")
	public List<SellerDTO> getAll(
			@PathVariable("page") Integer page,
			@PathVariable("size") Integer size)
	{
		return sellerService.getAll(page,size);
	}
}
