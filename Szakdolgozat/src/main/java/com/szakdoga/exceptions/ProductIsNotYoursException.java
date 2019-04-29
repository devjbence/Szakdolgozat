package com.szakdoga.exceptions;

public class ProductIsNotYoursException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProductIsNotYoursException(String message) {
		super(message);
	}
}
