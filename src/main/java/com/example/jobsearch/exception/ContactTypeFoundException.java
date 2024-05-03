package com.example.jobsearch.exception;

import java.util.NoSuchElementException;

public class ContactTypeFoundException extends NoSuchElementException {
    public ContactTypeFoundException(String message) {
        super(message);
    }
}