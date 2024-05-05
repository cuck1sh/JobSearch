package com.example.jobsearch.controller;

import com.example.jobsearch.dto.RespondMessengerDto;
import com.example.jobsearch.service.MessageService;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
@RequestMapping("messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final RespondedApplicantsService respondedApplicantsService;

    @PostMapping("{respondId}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void putMessage(@PathVariable int respondId, String message) {
        messageService.takeMessage(respondId, message);
    }

    @GetMapping("resumes/{resumeId}/vacancy/{vacancyId}")
    public String getMessenger(@PathVariable int resumeId, @PathVariable int vacancyId, Model model) {
        RespondMessengerDto messengerDto = respondedApplicantsService.getRespondMessenger(FileUtil.TEST_RESUME_ID, FileUtil.TEST_VACANCY_ID);
        model.addAttribute("messenger", messengerDto);
        return "pages/messenger";
    }

}
