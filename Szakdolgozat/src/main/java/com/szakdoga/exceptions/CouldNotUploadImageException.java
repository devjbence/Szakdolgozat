package com.szakdoga.exceptions;

public class CouldNotUploadImageException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CouldNotUploadImageException(String message) {
		super(message);
	}
}
