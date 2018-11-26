package com.szakdoga.exceptions;

public class ImageSizeIsTooBigException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ImageSizeIsTooBigException(String message) {
		super(message);
	}
}
