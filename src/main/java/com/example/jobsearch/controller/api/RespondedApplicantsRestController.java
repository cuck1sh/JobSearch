package com.example.jobsearch.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/responses")
@RequiredArgsConstructor
public class RespondedApplicantsRestController {

    //TODO УДАЛИТь ЕСЛИ НЕ НУЖНО

//    private final RespondedApplicantsService respondedApplicantsService;
//
//    @GetMapping("user")
//    public ResponseEntity<List<RespondedApplicantsDto>> getUserResponses(@RequestParam(name = "email") String email) {
//        return ResponseEntity.ok(respondedApplicantsService.getUserResponses(email));
//    }
//
//    @GetMapping("resumes/{resumeId}")
//    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForResume(@PathVariable int resumeId) {
//        return ResponseEntity.ok(respondedApplicantsService.getResponsesForResume(resumeId));
//    }
//
//    @GetMapping("vacancies/{vacancyId}")
//    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForVacancy(@PathVariable int vacancyId) {
//        return ResponseEntity.ok(respondedApplicantsService.getResponsesForVacancy(vacancyId));
//    }

}
