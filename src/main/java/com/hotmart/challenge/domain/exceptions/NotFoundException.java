package com.hotmart.challenge.domain.exceptions;

public class NotFoundException extends ValidationException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}
}