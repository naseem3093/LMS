package com.example.exceptions;

public class BookAlreadyBorrowedException extends RuntimeException {
  	private static final long serialVersionUID = 1L;

	public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}
