package com.example.jobsearch.exception;

import java.util.NoSuchElementException;

public class ResponseApiException extends NoSuchElementException {
    public ResponseApiException(String message) {
        super(message);
    }
}
