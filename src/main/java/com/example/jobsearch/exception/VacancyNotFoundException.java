package com.example.jobsearch.exception;

import java.util.NoSuchElementException;

public class VacancyNotFoundException extends NoSuchElementException {
    public VacancyNotFoundException(String message) {
        super(message);
    }
}
