package com.szakdoga.exceptions;

public class SellerDoesNotExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SellerDoesNotExistsException(String message) {
		super(message);
	}
}
