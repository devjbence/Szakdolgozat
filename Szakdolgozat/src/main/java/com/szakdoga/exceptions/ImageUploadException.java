package com.szakdoga.exceptions;

public class ImageUploadException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ImageUploadException(String message) {
		super(message);
	}
}
