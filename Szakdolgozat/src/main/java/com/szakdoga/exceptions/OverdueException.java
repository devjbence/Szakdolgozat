package com.szakdoga.exceptions;

public class OverdueException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public OverdueException(String message) {
		super(message);
	}
}
