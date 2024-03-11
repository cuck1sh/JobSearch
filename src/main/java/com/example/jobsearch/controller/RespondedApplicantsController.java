package com.example.jobsearch.controller;

import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.service.RespondedApplicantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RespondedApplicantsController {
    private final RespondedApplicantsService respondedApplicantsService;

    @GetMapping("responces/user")
    public ResponseEntity<List<RespondedApplicantsDto>> getUserResponses(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(respondedApplicantsService.getUserResponses(email));
    }

}
