package com.szakdoga.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.DTOs.SellerDTO;
import com.szakdoga.services.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {
	@Autowired
	private SellerService sellerService;

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> update(@RequestBody SellerDTO sellerDTO) throws Exception {
		sellerService.updateProfile(sellerDTO);
		return new ResponseEntity<>("Updated", HttpStatus.ACCEPTED);
	}

	// https://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/uploadProfileImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(
			@RequestPart("username") String username,
			@RequestPart("image") MultipartFile imageFile) throws IOException {
		sellerService.saveImage(username, imageFile);
		return new ResponseEntity<>("File is uploaded successfully", HttpStatus.ACCEPTED);
	}

	// https://stackoverflow.com/a/16725508
	@ResponseBody
	@RequestMapping(value = "/getProfileImage/username/{username}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@PathVariable("username") String username) {

		return sellerService.getProfileImage(username);
	}
}
