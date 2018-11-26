package com.szakdoga.exceptions;

public class BadYearException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BadYearException(String message) {
		super(message);
	}
}
