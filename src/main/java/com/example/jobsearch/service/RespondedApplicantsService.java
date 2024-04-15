package com.example.jobsearch.service;

import com.example.jobsearch.dto.RespondMessengerDto;
import com.example.jobsearch.dto.RespondedApplicantsDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RespondedApplicantsService {
    Integer getRespondId(int resumeId, int vacancyId);

    RespondMessengerDto getRespondMessenger(int resumeId, int vacancyId);
    List<RespondedApplicantsDto> getUserResponses(String email);
    List<RespondedApplicantsDto> getResponsesForVacancy(int vacancyId);
    List<RespondedApplicantsDto> getResponsesForResume(int resumeId);
    List<RespondedApplicantsDto> getResponsesForEmployee(int userId);
    List<RespondedApplicantsDto> getResponsesForEmployer(int userId);
    HttpStatus sendResponseForVacancy(Authentication auth, int vacancyId, int resumeId);
}
