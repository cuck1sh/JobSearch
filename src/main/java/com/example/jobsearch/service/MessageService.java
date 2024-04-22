package com.example.jobsearch.service;

import com.example.jobsearch.dto.user.UserDto;
import org.springframework.http.ResponseEntity;

public interface MessageService {
    ResponseEntity<?> getMessages(UserDto userDto, int respond);

    void takeMessage(int respond, String message);
}
