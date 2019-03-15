package com.szakdoga.exceptions;

public class AlreadySoldException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AlreadySoldException(String message) {
		super(message);
	}
}
