package com.szakdoga.exceptions;

public class ImageDoesNotExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ImageDoesNotExistsException(String message) {
		super(message);
	}
}
