package com.szakdoga.exceptions;

public class BadProfileTypeException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BadProfileTypeException(String message) {
		super(message);
	}
}
