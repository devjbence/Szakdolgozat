package com.szakdoga.exceptions;

public class UsernameMissingForProfileUpdateException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UsernameMissingForProfileUpdateException(String message) {
		super(message);
	}
}
