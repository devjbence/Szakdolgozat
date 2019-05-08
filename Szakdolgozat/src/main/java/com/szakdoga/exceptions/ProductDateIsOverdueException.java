package com.szakdoga.exceptions;

public class ProductDateIsOverdueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProductDateIsOverdueException(String message) {
		super(message);
	}
}
