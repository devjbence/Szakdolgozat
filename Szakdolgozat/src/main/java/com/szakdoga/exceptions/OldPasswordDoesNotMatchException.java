package com.szakdoga.exceptions;

public class OldPasswordDoesNotMatchException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public OldPasswordDoesNotMatchException(String message) {
		super(message);
	}
}
