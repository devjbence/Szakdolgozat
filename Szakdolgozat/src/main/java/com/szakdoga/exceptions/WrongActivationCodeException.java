package com.szakdoga.exceptions;

public class WrongActivationCodeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public WrongActivationCodeException(String message) {
		super(message);
	}
}
