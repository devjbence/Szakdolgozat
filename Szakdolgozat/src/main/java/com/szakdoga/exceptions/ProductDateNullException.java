package com.szakdoga.exceptions;

public class ProductDateNullException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProductDateNullException(String message) {
		super(message);
	}
}
