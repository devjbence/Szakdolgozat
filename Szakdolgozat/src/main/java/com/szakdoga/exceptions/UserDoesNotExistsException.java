package com.szakdoga.exceptions;

public class UserDoesNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserDoesNotExistsException(String message) {
		super(message);
	}
}
