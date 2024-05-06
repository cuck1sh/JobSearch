package com.example.jobsearch.exception;

import java.util.NoSuchElementException;

public class AccessException extends NoSuchElementException {
    public AccessException(String message) {
        super(message);
    }
}
