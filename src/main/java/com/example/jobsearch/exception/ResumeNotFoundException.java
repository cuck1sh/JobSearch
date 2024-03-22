package com.example.jobsearch.exception;

import java.util.NoSuchElementException;

public class ResumeNotFoundException extends NoSuchElementException {
    public ResumeNotFoundException(String message) {
        super(message);
    }
}
