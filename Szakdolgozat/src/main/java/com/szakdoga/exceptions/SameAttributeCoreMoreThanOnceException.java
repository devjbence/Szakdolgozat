package com.szakdoga.exceptions;

public class SameAttributeCoreMoreThanOnceException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SameAttributeCoreMoreThanOnceException(String message) {
		super(message);
	}
}
