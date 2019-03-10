package com.szakdoga.exceptions;

public class CategoryDoesNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CategoryDoesNotExistsException(String message) {
		super(message);
	}
}
