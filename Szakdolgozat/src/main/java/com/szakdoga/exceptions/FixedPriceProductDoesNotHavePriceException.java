package com.szakdoga.exceptions;

public class FixedPriceProductDoesNotHavePriceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FixedPriceProductDoesNotHavePriceException(String message) {
		super(message);
	}
}
