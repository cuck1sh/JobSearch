package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.RespondedApplicantsDao;
import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.RespondedApplicants;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RespondedApplicantsServiceImpl implements RespondedApplicantsService {
    private final RespondedApplicantsDao respondedApplicantsDao;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    @Override
    public List<RespondedApplicantsDto> getUserResponses(String email) {
        List<RespondedApplicants> applicants = respondedApplicantsDao.getUserResponses(email);
        List<RespondedApplicantsDto> dtos = new ArrayList<>();
        applicants.forEach(e -> {
            try {
                dtos.add(RespondedApplicantsDto.builder()
                        .resume(resumeService.getResumeById(e.getResumeId()))
                        .vacancy(vacancyService.getVacancyById(e.getVacancyId()))
                        .confirmation(e.getConfirmation())
                        .build());
            } catch (UserNotFoundException ex) {
                log.error(ex.getMessage());
            }
        });
        return dtos;
    }

    @SneakyThrows
    @Override
    public List<RespondedApplicantsDto> getResponsesForVacancy(int vacancyId) {
        List<RespondedApplicants> applicants = respondedApplicantsDao.getResponsesForVacancy(vacancyId);
        List<RespondedApplicantsDto> dtos = new ArrayList<>();

        applicants.forEach(e -> {
            try {
                dtos.add(RespondedApplicantsDto.builder()
                        .resume(resumeService.getResumeById(e.getResumeId()))
                        .vacancy(vacancyService.getVacancyById(e.getVacancyId()))
                        .confirmation(e.getConfirmation())
                        .build());
            } catch (UserNotFoundException ex) {
                log.error(ex.getMessage());
            }
        });
        return dtos;
    }
}
