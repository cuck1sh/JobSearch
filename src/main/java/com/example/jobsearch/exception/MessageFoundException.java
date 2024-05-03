package com.example.jobsearch.exception;

import java.util.NoSuchElementException;

public class MessageFoundException extends NoSuchElementException {
    public MessageFoundException(String message) {
        super(message);
    }
}
