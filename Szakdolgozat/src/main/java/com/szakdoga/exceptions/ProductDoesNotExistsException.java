package com.szakdoga.exceptions;

public class ProductDoesNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProductDoesNotExistsException(String message) {
		super(message);
	}
}
