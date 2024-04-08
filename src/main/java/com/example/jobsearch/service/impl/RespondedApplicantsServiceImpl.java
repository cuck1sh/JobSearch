package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.RespondedApplicantsDao;
import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.exception.VacancyNotFoundException;
import com.example.jobsearch.model.RespondedApplicants;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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

    @Override
    public List<RespondedApplicantsDto> getResponsesForVacancy(int vacancyId) {
        if (!userService.isEmployee(vacancyService.getVacancyById(vacancyId).getUserEmail())) {
            List<RespondedApplicants> applicants = respondedApplicantsDao.getResponsesForVacancy(vacancyId);
            return getRespondedApplicantsDtos(applicants);
        }
        log.error("vacancy check by non-employer");
        throw new UserNotFoundException("vacancy check by non-employer");
    }

    @SneakyThrows
    @Override
    public List<RespondedApplicantsDto> getResponsesForResume(int resumeId) {
        if (resumeService.isResumeInSystem(resumeId)) {
            if (userService.isEmployee(resumeService.getResumeById(resumeId).getUserEmail())) {
                List<RespondedApplicants> applicants = respondedApplicantsDao.getResponsesForResume(resumeId);
                return getRespondedApplicantsDtos(applicants);
            }
            throw new ResumeNotFoundException("Юзер " + resumeService.getResumeById(resumeId).getUserEmail() + " не найден среди соискателей");
        }
        throw new ResumeNotFoundException("Резюме с айди " + resumeId + " не найдено в системе для выдачи списка откликов на него");
    }

    @Override
    public List<RespondedApplicantsDto> getResponsesForEmployee(int userId) {
        if (userService.isUserInSystem(userId)) {
            if (userService.isEmployee(userId)) {
                List<RespondedApplicants> applicants = respondedApplicantsDao.getResponsesForEmployee(userId);
                return getRespondedApplicantsDtos(applicants);
            }
            throw new ResumeNotFoundException("Юзер " + userId + " не найден среди соискателей");
        }
        throw new UserNotFoundException("Не найден юзер с айди: " + userId);
    }

    @Override
    public List<RespondedApplicantsDto> getResponsesForEmployer(int userId) {
        if (!userService.isEmployee(userId)) {
            List<RespondedApplicants> applicants = respondedApplicantsDao.getResponsesForEmployer(userId);
            return getRespondedApplicantsDtos(applicants);
        }
        throw new VacancyNotFoundException("Юзер " + userId + " не найден среди работодателей");
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

    @Override
    public HttpStatus sendResponseForVacancy(Authentication auth, int vacancyId, int resumeId) {
        User user = (User) auth.getPrincipal();
        if (resumeService.isResumeInSystem(resumeId)) {
            if (userService.isEmployee(user.getUsername())) {
                if (user.getUsername().equals(userService.getUserByEmail(resumeService.getResumeById(resumeId).getUserEmail()).getEmail())) {
                    if (vacancyService.isVacancyInSystem(vacancyId) && vacancyService.isVacancyActive(vacancyId)) {
                        respondedApplicantsDao.createResponse(vacancyId, resumeId);
                        return HttpStatus.OK;
                    }
                    throw new VacancyNotFoundException("Вакансия с айди: " + vacancyId + "не найдена в системе или не активна");
                }
                throw new ResumeNotFoundException("Не найдено совпдаение Юзера " + user.getUsername() + " с юзером указанным в резюме");
            }
            throw new ResumeNotFoundException("Юзер " + user.getUsername() + " не найден среди соискателей");
        }
        throw new ResumeNotFoundException("Резюме с айди " + resumeId + " не найдено в системе для отклика на вакансию");
    }
}
