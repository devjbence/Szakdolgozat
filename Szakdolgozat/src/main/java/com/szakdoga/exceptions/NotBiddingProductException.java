package com.szakdoga.exceptions;

public class NotBiddingProductException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NotBiddingProductException(String message) {
		super(message);
	}
}
