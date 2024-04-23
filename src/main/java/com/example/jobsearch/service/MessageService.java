package com.example.jobsearch.service;

import com.example.jobsearch.dto.user.AuthUserDto;
import org.springframework.http.ResponseEntity;

public interface MessageService {
    ResponseEntity<?> getMessages(AuthUserDto authUserDto, int respond);

    void takeMessage(int respond, String message);
}
