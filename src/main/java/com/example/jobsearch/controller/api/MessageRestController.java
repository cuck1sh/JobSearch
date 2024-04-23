package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.user.AuthUserDto;
import com.example.jobsearch.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/messages")
@RequiredArgsConstructor
public class MessageRestController {
    private final MessageService messageService;

    @PostMapping("{respondedApplicantsId}")
    public ResponseEntity<?> getMessages(@PathVariable int respondedApplicantsId,
                                         @RequestBody AuthUserDto authUserDto) {
        return messageService.getMessages(authUserDto, respondedApplicantsId);
    }
}
