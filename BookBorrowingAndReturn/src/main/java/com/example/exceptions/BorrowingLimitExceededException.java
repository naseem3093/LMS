package com.example.exceptions;

public class BorrowingLimitExceededException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BorrowingLimitExceededException(String message) {
        super(message);
    }
}
