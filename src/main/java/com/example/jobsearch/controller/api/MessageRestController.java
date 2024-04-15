package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.MessageDto;
import com.example.jobsearch.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/messages")
@RequiredArgsConstructor
public class MessageRestController {
    private final MessageService messageService;

    @GetMapping("{respondedApplicantsId}")
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable int respondedApplicantsId) {
        return ResponseEntity.ok(messageService.getMessages(respondedApplicantsId));
    }
}
