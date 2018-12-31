package com.szakdoga.exceptions;

public class ProductCommentDoesNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProductCommentDoesNotExistsException(String message) {
		super(message);
	}
}
