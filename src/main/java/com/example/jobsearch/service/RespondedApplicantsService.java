package com.example.jobsearch.service;

import com.example.jobsearch.dto.RespondMessengerDto;
import com.example.jobsearch.dto.RespondedApplicantsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.List;

public interface RespondedApplicantsService {
    void getVacancy(int id, Model model);

    String getResume(int id, Model model);

    Integer getRespondId(int resumeId, int vacancyId);

    RespondedApplicantsDto getRespondedApplicants(int respond);

    Boolean isRespondInSystem(int respond);

    RespondMessengerDto getRespondMessenger(int respondId);
    List<RespondedApplicantsDto> getUserResponses(String email);
    List<RespondedApplicantsDto> getResponsesForVacancy(int vacancyId);
    List<RespondedApplicantsDto> getResponsesForResume(int resumeId);

    List<RespondedApplicantsDto> getUsersResponses(int userId);

    List<RespondedApplicantsDto> getResponsesForVacancy(int vacancyId, int userId);
    ResponseEntity<Integer> createResponse(int vacancyId, int resumeId);

    void getResumeAndVacancyByResponseId(Integer id, Model model);


}
