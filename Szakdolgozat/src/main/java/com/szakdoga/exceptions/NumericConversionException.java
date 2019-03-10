package com.szakdoga.exceptions;

public class NumericConversionException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NumericConversionException(String message) {
		super(message);
	}
}
