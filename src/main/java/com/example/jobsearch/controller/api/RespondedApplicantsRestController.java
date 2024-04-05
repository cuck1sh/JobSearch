package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.service.RespondedApplicantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/responses")
@RequiredArgsConstructor
public class RespondedApplicantsRestController {
    private final RespondedApplicantsService respondedApplicantsService;

    @GetMapping("user")
    public ResponseEntity<List<RespondedApplicantsDto>> getUserResponses(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(respondedApplicantsService.getUserResponses(email));
    }

    @GetMapping("resumes/{resumeId}")
    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForResume(@PathVariable int resumeId) {
        return ResponseEntity.ok(respondedApplicantsService.getResponsesForResume(resumeId));
    }

    @GetMapping("vacancies/{vacancyId}")
    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForVacancy(@PathVariable int vacancyId) {
        return ResponseEntity.ok(respondedApplicantsService.getResponsesForVacancy(vacancyId));
    }

}
