package com.example.jobsearch.service.impl;


import com.example.jobsearch.dto.MessageDto;
import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.exception.MessageFoundException;
import com.example.jobsearch.model.Message;
import com.example.jobsearch.model.RespondedApplicants;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.MessagesRepository;
import com.example.jobsearch.service.MessageService;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
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
    private final MessagesRepository messagesRepository;
    private final RespondedApplicantsService respondedApplicantsService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public ResponseEntity<?> getMessages(int respond) {
        UserDto user = authenticatedUserProvider.getAuthUser();

        List<Message> messages = messagesRepository.findAllByRespondedApplicantId(respond);
        RespondedApplicantsDto respondedApplicantsDto = respondedApplicantsService.getRespondedApplicants(respond);

        String targetInfo = user.getAccountType().equals("EMPLOYER")
                ? respondedApplicantsDto.getVacancy().getUserEmail()
                : respondedApplicantsDto.getResume().getUserEmail();

        if (targetInfo.equals(user.getEmail())) {
            List<MessageDto> messageDtos = new ArrayList<>();

            messages.forEach(e -> messageDtos.add(MessageDto.builder()
                    .respondedApplicantsId(e.getRespondedApplicant().getId())
                    .userEmail(e.getUser().getEmail())
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
            UserDto user = authenticatedUserProvider.getAuthUser();
            Message newMessage = Message.builder()
                    .respondedApplicant(RespondedApplicants.builder().id(respond).build())
                    .user(User.builder().id(user.getId()).build())
                    .content(message)
                    .timestamp(LocalDateTime.now())
                    .build();
            messagesRepository.save(newMessage);
        } else {
            log.error("Сообщение пусто для отклика с айди :" + respond + " пусто");
            throw new MessageFoundException("Сообщение пусто");
        }
    }
}
