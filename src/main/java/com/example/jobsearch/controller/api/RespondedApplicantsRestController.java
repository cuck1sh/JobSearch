package com.example.jobsearch.controller.api;

import com.example.jobsearch.service.RespondedApplicantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/responses")
@RequiredArgsConstructor
public class RespondedApplicantsRestController {
    private final RespondedApplicantsService respondedApplicantsService;

    @GetMapping("vacancy/{vacancyId}/resume/{resumeId}")
    public ResponseEntity<Integer> createResponse(@PathVariable int vacancyId, @PathVariable int resumeId) {
        return respondedApplicantsService.createResponse(vacancyId, resumeId);
    }

}
