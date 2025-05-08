package com.example.exceptions;

public class BookDeletionException extends RuntimeException {
	public BookDeletionException(String message) {
		super(message);
	}
}
