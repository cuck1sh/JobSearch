package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.RespondedApplicantsDao;
import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.model.RespondedApplicants;
import com.example.jobsearch.service.RespondedApplicantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RespondedApplicantsServiceImpl implements RespondedApplicantsService {
    private final RespondedApplicantsDao respondedApplicantsDao;

    @Override
    public List<RespondedApplicantsDto> getUserResponses(String email) {
        List<RespondedApplicants> applicants = respondedApplicantsDao.getUserResponses(email);
        List<RespondedApplicantsDto> dtos = new ArrayList<>();
        applicants.forEach(e -> dtos.add(RespondedApplicantsDto.builder()
                .id(e.getId())
                .resumeId(e.getResumeId())
                .vacancyId(e.getVacancyId())
                .confirmation(e.getConfirmation())
                .build()));
        return dtos;
    }

    @Override
    public List<RespondedApplicantsDto> getResponsesForVacancy(String name) {
        List<RespondedApplicants> applicants = respondedApplicantsDao.getResponsesForVacancy(name);
        List<RespondedApplicantsDto> dtos = new ArrayList<>();
        applicants.forEach(e -> dtos.add(RespondedApplicantsDto.builder()
                .id(e.getId())
                .resumeId(e.getResumeId())
                .vacancyId(e.getVacancyId())
                .confirmation(e.getConfirmation())
                .build()));
        return dtos;
    }
}
