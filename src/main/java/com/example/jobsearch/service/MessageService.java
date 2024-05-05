package com.example.jobsearch.service;

import org.springframework.http.ResponseEntity;

public interface MessageService {
    ResponseEntity<?> getMessages(int respond);

    void takeMessage(int respond, String message);
}
