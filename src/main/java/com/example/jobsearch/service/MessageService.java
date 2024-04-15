package com.example.jobsearch.service;

import com.example.jobsearch.dto.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getMessages(int respond);

    void takeMessage(int respond, String message);
}
