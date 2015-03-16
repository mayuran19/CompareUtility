package com.mayuran19.configuration.common.exception;

public class ApplicationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(Throwable throwable) {
		super(throwable);
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
