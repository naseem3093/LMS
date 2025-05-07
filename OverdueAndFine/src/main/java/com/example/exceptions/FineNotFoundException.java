package com.example.exceptions;

public class FineNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public FineNotFoundException(String message) {
        super(message);
    }
}
