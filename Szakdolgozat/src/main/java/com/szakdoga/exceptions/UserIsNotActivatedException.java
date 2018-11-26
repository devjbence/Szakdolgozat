package com.szakdoga.exceptions;

public class UserIsNotActivatedException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserIsNotActivatedException(String message) {
		super(message);
	}
}
