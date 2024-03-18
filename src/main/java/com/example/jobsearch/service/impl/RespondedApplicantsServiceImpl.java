package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.RespondedApplicantsDao;
import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.RespondedApplicants;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
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
    private final UserService userService;

    @Override
    public List<RespondedApplicantsDto> getUserResponses(String email) {
        List<RespondedApplicants> applicants = respondedApplicantsDao.getUserResponses(email);
        return getRespondedApplicantsDtos(applicants);
    }

    @SneakyThrows
    private boolean isEmployee(int userId) {
        return "Соискатель".equalsIgnoreCase(userService.getUserById(userId).getAccountType());
    }

    @SneakyThrows
    @Override
    public List<RespondedApplicantsDto> getResponsesForVacancy(int vacancyId) {
        if (!isEmployee(vacancyService.getVacancyById(vacancyId).getUserId())) {
            List<RespondedApplicants> applicants = respondedApplicantsDao.getResponsesForVacancy(vacancyId);
            return getRespondedApplicantsDtos(applicants);
        }
        log.error("vacancy check by non-employer");
        return null;
    }

    @SneakyThrows
    @Override
    public List<RespondedApplicantsDto> getResponsesForResume(int resumeId) {
        if (isEmployee(resumeService.getResumeById(resumeId).getUser().getId())) {
            List<RespondedApplicants> applicants = respondedApplicantsDao.getResponsesForResume(resumeId);
            return getRespondedApplicantsDtos(applicants);
        }
        log.error("vacancy check by non-employee");
        return null;
    }

    @Override
    public List<RespondedApplicantsDto> getResponsesForEmployee(int userId) {
        if (isEmployee(userId)) {
            List<RespondedApplicants> applicants = respondedApplicantsDao.getResponsesForEmployee(userId);
            return getRespondedApplicantsDtos(applicants);
        }
        log.error("vacancy check by non-employee");
        return null;
    }

    @Override
    public List<RespondedApplicantsDto> getResponsesForEmployer(int userId) {
        if (!isEmployee(userId)) {
            List<RespondedApplicants> applicants = respondedApplicantsDao.getResponsesForEmployer(userId);
            return getRespondedApplicantsDtos(applicants);
        }
        log.error("vacancy check by non-employer");
        return null;
    }

    private List<RespondedApplicantsDto> getRespondedApplicantsDtos(List<RespondedApplicants> applicants) {
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
