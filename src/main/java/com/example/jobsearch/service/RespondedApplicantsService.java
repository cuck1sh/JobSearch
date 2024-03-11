package com.example.jobsearch.service;

import com.example.jobsearch.dto.RespondedApplicantsDto;

import java.util.List;

public interface RespondedApplicantsService {
    List<RespondedApplicantsDto> getUserResponses(String email);

    List<RespondedApplicantsDto> getResponsesForVacancy(String name);
}
