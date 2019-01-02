package com.szakdoga.exceptions;

public class NotOwnUsernameException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NotOwnUsernameException(String message) {
		super(message);
	}
}
