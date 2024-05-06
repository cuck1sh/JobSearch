package com.example.jobsearch.service;

import com.example.jobsearch.dto.RespondMessengerDto;
import com.example.jobsearch.dto.RespondedApplicantsDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RespondedApplicantsService {
    Integer getRespondId(int resumeId, int vacancyId);

    RespondedApplicantsDto getRespondedApplicants(int respond);

    Boolean isRespondInSystem(int respond);

    RespondMessengerDto getRespondMessenger(int respondId);
    List<RespondedApplicantsDto> getUserResponses(String email);
    List<RespondedApplicantsDto> getResponsesForVacancy(int vacancyId);
    List<RespondedApplicantsDto> getResponsesForResume(int resumeId);
    List<RespondedApplicantsDto> getResponsesForEmployee(int userId);
    List<RespondedApplicantsDto> getResponsesForEmployer(int userId);

    ResponseEntity<Integer> createResponse(int vacancyId, int resumeId);
}
