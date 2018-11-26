package com.szakdoga.exceptions;

public class UserameAlreadyRegisteredException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserameAlreadyRegisteredException(String message) {
		super(message);
	}
}
