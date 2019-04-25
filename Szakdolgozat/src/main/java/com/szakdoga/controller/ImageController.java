package com.szakdoga.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.szakdoga.entities.DTOs.ImageDTO;
import com.szakdoga.services.interfaces.ImageService;
import com.szakdoga.utils.Utils;

@RequestMapping("/image")
@RestController()
public class ImageController {

	@Autowired
	private ImageService imageService;
	
	@ResponseBody
	@GetMapping(path="/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getById(@PathVariable("id") int id, HttpServletResponse response)
	{
		ImageDTO dto = imageService.get(id);
		
		if(dto == null)
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		return dto.getFile();
	}
	
	@ResponseBody
	@GetMapping(path="/profile/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] profileImagGetById(@PathVariable("id") int id, HttpServletResponse response)
	{
		ImageDTO dto = imageService.get(id);
		
		if(dto == null)
			return Utils.getDefaultProfileImage();
		
		return dto.getFile();
	}
}
