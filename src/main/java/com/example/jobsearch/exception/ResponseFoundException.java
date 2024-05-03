package com.example.jobsearch.exception;

import java.util.NoSuchElementException;

public class ResponseFoundException extends NoSuchElementException {
    public ResponseFoundException(String message) {
        super(message);
    }
}
