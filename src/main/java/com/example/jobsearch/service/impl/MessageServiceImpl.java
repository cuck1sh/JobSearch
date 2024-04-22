package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.MessageDao;
import com.example.jobsearch.dto.MessageDto;
import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.model.Message;
import com.example.jobsearch.service.MessageService;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageDao messageDao;
    private final UserService userService;
    private final RespondedApplicantsService respondedApplicantsService;
    private final ResumeService resumeService;

    @Override
    public ResponseEntity<?> getMessages(UserDto userDto, int respond) {
        List<Message> messages = messageDao.getMessages(respond);
        RespondedApplicantsDto respondedApplicantsDto = respondedApplicantsService.getRespondedApplicants(respond);

        if (respondedApplicantsDto.getResume().getUserEmail().equals(userDto.getEmail())) {
            List<MessageDto> messageDtos = new ArrayList<>();

            messages.forEach(e -> messageDtos.add(MessageDto.builder()
                    .respondedApplicantsId(e.getRespondedApplicantsId())
                    .userEmail(userService.getUserById(e.getUserId()).getEmail())
                    .content(e.getContent())
                    .timestamp(e.getTimestamp())
                    .build()));
            return ResponseEntity.ok(messageDtos);
        } else {
            log.error("Несоответсвие юзера и юзера в резюме для вывода сообщений");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Несоответсвие юзера и юзера в резюме для вывода сообщений");
        }
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
