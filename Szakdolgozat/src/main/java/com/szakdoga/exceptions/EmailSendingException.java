package com.szakdoga.exceptions;

public class EmailSendingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmailSendingException(String message) {
		super(message);
	}
}
