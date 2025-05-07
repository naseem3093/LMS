package com.example.exceptions;

public class BookNotFound extends Exception {
    private static final long serialVersionUID = 1L; // Defines a unique ID

    public BookNotFound(String message) {
        super(message);
    }
}
