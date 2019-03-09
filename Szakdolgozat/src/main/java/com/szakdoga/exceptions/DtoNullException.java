package com.szakdoga.exceptions;

public class DtoNullException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DtoNullException(String message) {
		super(message);
	}
}
