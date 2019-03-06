package com.szakdoga.exceptions;

public class BuyerDoesNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BuyerDoesNotExistsException(String message) {
		super(message);
	}
}
