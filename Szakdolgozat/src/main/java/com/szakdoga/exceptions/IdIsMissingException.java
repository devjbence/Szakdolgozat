package com.szakdoga.exceptions;

public class IdIsMissingException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public IdIsMissingException(String message) {
		super(message);
	}
}
