package com.example.jobsearch.exception;

import java.util.NoSuchElementException;

public class UserAuthenticationException extends NoSuchElementException {
    public UserAuthenticationException(String message) {
        super(message);
    }
}
