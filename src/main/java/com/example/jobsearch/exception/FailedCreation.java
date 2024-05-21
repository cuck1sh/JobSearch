package com.example.jobsearch.exception;

import java.util.NoSuchElementException;

public class FailedCreation extends NoSuchElementException {
    public FailedCreation(String message) {
        super(message);
    }
}
