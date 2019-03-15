package com.szakdoga.exceptions;

public class NotFixedPricedProductException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NotFixedPricedProductException(String message) {
		super(message);
	}
}
