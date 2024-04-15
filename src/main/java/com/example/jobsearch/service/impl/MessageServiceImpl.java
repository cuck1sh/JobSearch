package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.MessageDao;
import com.example.jobsearch.dto.MessageDto;
import com.example.jobsearch.model.Message;
import com.example.jobsearch.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageDao messageDao;

    @Override
    public List<MessageDto> getMessages(int respond) {
        List<Message> messages = messageDao.getMessages(respond);
        List<MessageDto> messageDtos = new ArrayList<>();

        messages.forEach(e -> messageDtos.add(MessageDto.builder()
                .id(e.getId())
                .content(e.getContent())
                .timestamp(e.getTimestamp())
                .build()));
        return messageDtos;
    }

    @Override
    public void takeMessage(int respond, String message) {
        if (!message.isBlank()) {
            Message newMessage = Message.builder()
                    .respondedApplicantsId(respond)
                    .content(message)
                    .timestamp(LocalDateTime.now())
                    .build();
            messageDao.createMessage(newMessage);
        } else {
            log.error("Сообщение пусто для отклика с айди :" + respond + " пусто");
        }


    }
}
