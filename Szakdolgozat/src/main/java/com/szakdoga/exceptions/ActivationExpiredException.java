package com.szakdoga.exceptions;

public class ActivationExpiredException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ActivationExpiredException(String message) {
		super(message);
	}
}
