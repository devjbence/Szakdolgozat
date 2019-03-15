package com.szakdoga.exceptions;

public class SmallerPriceException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SmallerPriceException(String message) {
		super(message);
	}
}
