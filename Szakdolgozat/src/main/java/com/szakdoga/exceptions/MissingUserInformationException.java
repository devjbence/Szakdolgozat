package com.szakdoga.exceptions;

public class MissingUserInformationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public MissingUserInformationException(String message) {
		super(message);
	}
}
