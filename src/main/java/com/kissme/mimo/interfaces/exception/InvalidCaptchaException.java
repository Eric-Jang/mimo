package com.kissme.mimo.interfaces.exception;

/**
 * 
 * @author loudyn
 *
 */
public class InvalidCaptchaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidCaptchaException() {
		super();
	}

	public InvalidCaptchaException(String message) {
		super(message);
	}
}
