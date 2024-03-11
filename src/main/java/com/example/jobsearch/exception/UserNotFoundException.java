package com.example.jobsearch.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
