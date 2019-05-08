package com.szakdoga.exceptions;

public class CannotBuyYourOwnProductException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CannotBuyYourOwnProductException(String message) {
		super(message);
	}
}
