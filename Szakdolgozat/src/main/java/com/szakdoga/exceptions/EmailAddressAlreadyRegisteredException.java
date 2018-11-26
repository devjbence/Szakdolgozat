package com.szakdoga.exceptions;

public class EmailAddressAlreadyRegisteredException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EmailAddressAlreadyRegisteredException(String message) {
		super(message);
	}
}
