package com.szakdoga.exceptions;

public class AttributeNameDoesNotExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AttributeNameDoesNotExistsException(String message) {
		super(message);
	}
}
