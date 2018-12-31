package com.szakdoga.exceptions;

public class ProductToUserDoesNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProductToUserDoesNotExistsException(String message) {
		super(message);
	}
}
