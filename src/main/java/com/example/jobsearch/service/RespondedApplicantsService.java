package com.example.jobsearch.service;

import com.example.jobsearch.dto.RespondedApplicantsDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface RespondedApplicantsService {
    List<RespondedApplicantsDto> getUserResponses(String email);
    List<RespondedApplicantsDto> getResponsesForVacancy(int vacancyId);
    List<RespondedApplicantsDto> getResponsesForResume(int resumeId);
    List<RespondedApplicantsDto> getResponsesForEmployee(int userId);
    List<RespondedApplicantsDto> getResponsesForEmployer(int userId);

    HttpStatus sendResponseForVacancy(int userId, int vacancyId, int resumeId);
}
