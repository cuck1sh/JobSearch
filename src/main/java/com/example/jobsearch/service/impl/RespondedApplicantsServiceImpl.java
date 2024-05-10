package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.RespondMessengerDto;
import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.dto.resume.ResumeDto;
import com.example.jobsearch.dto.user.ProfileDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.dto.vacancy.VacancyDto;
import com.example.jobsearch.exception.AccessException;
import com.example.jobsearch.exception.ResponseFoundException;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.exception.VacancyNotFoundException;
import com.example.jobsearch.model.RespondedApplicants;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.repository.RespondedApplicantsRepository;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RespondedApplicantsServiceImpl implements RespondedApplicantsService {
    private final RespondedApplicantsRepository repository;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final UserService userService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public void getVacancy(int id, Model model) {

        if (vacancyService.isVacancyInSystem(id)) {
            VacancyDto vacancyDto = vacancyService.getVacancyById(id);
            UserDto user = authenticatedUserProvider.getAuthUser();

            if (user.getId() != null) {
                if (userService.isEmployee(user.getId())) {
                    List<ResumeDto> resumes = resumeService.getResumesByUserId(user.getId());
                    model.addAttribute("resumes", resumes);
                } else {
                    List<RespondedApplicantsDto> responses = getResponsesForEmployer(id, user.getId());
                    model.addAttribute("responses", responses);
                    model.addAttribute("responsesQty", responses.size());
                }
            }

            model.addAttribute("vacancy", vacancyDto);
        } else {
            throw new VacancyNotFoundException("Не найдена вакансия");
        }
    }

    @Override
    public Integer getRespondId(int resumeId, int vacancyId) {
        return repository.findRespondedApplicantsByResumeIdAndVacancyId(resumeId, vacancyId)
                .orElseThrow(() -> new ResponseFoundException("Не найден отклик по резюмеАйди: " + resumeId + " и вакансииАйди: " + vacancyId))
                .getId();
    }

    @Override
    public Boolean isRespondInSystem(int respond) {
        return repository.existsById(respond);
    }

    @Override
    public RespondedApplicantsDto getRespondedApplicants(int respond) {
        if (isRespondInSystem(respond)) {
            RespondedApplicants respondedApplicants = repository.findById(respond)
                    .orElseThrow(() -> new ResponseFoundException("Не найден отклик c айди: " + respond));

            return RespondedApplicantsDto.builder()
                    .id(respondedApplicants.getId())
                    .vacancy(vacancyService.getVacancyById(respondedApplicants.getVacancy().getId()))
                    .resume(resumeService.getResumeById(respondedApplicants.getResume().getId()))
                    .confirmation(respondedApplicants.getConfirmation())
                    .build();
        } else {
            log.error("Не найден отклик для getRespondedApplicants()");
            return null;
        }

    }

    @Override
    public RespondMessengerDto getRespondMessenger(int respondId) {
        UserDto authUser = authenticatedUserProvider.getAuthUser();
        RespondedApplicantsDto response = getRespondedApplicants(respondId);

        UserDto employee = userService.getUserByEmail(response.getResume().getUserEmail());
        UserDto employer = userService.getUserByEmail(response.getVacancy().getUserEmail());

        UserDto profileDto = authUser.getEmail().equals(employee.getEmail()) ? employer : employee;

        if (authUser.getEmail().equals(employee.getEmail()) || authUser.getEmail().equals(employer.getEmail())) {
            ProfileDto profile = ProfileDto.builder()
                    .id(profileDto.getId())
                    .name(profileDto.getName())
                    .email(profileDto.getEmail())
                    .age(profileDto.getAge())
                    .phoneNumber(profileDto.getPhoneNumber())
                    .avatar(profileDto.getAvatar())
                    .accountType(profileDto.getAccountType())
                    .build();

            return RespondMessengerDto.builder()
                    .respondedApplicantsId(respondId)
                    .employerProfile(profile)
                    .build();
        }
        log.error("Попытка доступа к сообщениям чужого юзера getRespondMessenger()");
        throw new AccessException("Попытка доступа к сообщениям чужого юзера");
    }

    @Override
    public List<RespondedApplicantsDto> getUserResponses(String email) {
        List<RespondedApplicants> applicants = repository.findRespondedApplicantsByEmployeeEmail(email);
        return getRespondedApplicantsDtos(applicants);
    }

    @Override
    public List<RespondedApplicantsDto> getResponsesForVacancy(int vacancyId) {
        if (!userService.isEmployee(vacancyService.getVacancyById(vacancyId).getUserEmail())) {
            List<RespondedApplicants> applicants = repository.findAllByVacancyId(vacancyId);
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
                List<RespondedApplicants> applicants = repository.findAllByResumeId(resumeId);
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
                List<RespondedApplicants> applicants = repository.findAllByResumeUserId(userId);
                return getRespondedApplicantsDtos(applicants);
            }
            throw new ResumeNotFoundException("Юзер " + userId + " не найден среди соискателей");
        }
        throw new UserNotFoundException("Не найден юзер с айди: " + userId);
    }

    @Override
    public List<RespondedApplicantsDto> getResponsesForEmployer(int vacancyId, int userId) {
        if (!userService.isEmployee(userId)) {
            List<RespondedApplicants> applicants = repository.findAllByVacancyIdAndVacancyUserId(vacancyId, userId);
            return getRespondedApplicantsDtos(applicants);
        }
        log.error("Юзер " + userId + " не найден среди работодателей");
        throw new VacancyNotFoundException("Юзер " + userId + " не найден среди работодателей");
    }

    private List<RespondedApplicantsDto> getRespondedApplicantsDtos(List<RespondedApplicants> applicants) {
        List<RespondedApplicantsDto> dtos = new ArrayList<>();

        applicants.forEach(e -> {
            try {
                dtos.add(RespondedApplicantsDto.builder()
                        .id(e.getId())
                        .resume(resumeService.getResumeById(e.getResume().getId()))
                        .vacancy(vacancyService.getVacancyById(e.getVacancy().getId()))
                        .confirmation(e.getConfirmation())
                        .build());
            } catch (UserNotFoundException ex) {
                log.error(ex.getMessage());
            }
        });
        return dtos;
    }

    @Override
    public ResponseEntity<Integer> createResponse(int vacancyId, int resumeId) {
        UserDto user = authenticatedUserProvider.getAuthUser();
        if (resumeService.isResumeInSystem(resumeId)) {
            if (userService.isEmployee(user.getEmail())) {
                if (user.getEmail().equals(userService.getUserByEmail(resumeService.getResumeById(resumeId).getUserEmail()).getEmail())) {
                    if (vacancyService.isVacancyInSystem(vacancyId) && vacancyService.isVacancyActive(vacancyId)) {
                        return ResponseEntity.ok(
                                repository.save(RespondedApplicants.builder()
                                .vacancy(Vacancy.builder().id(vacancyId).build())
                                .resume(Resume.builder().id(resumeId).build())
                                        .build()).getId()
                        );


                    }
                    throw new VacancyNotFoundException("Вакансия с айди: " + vacancyId + "не найдена в системе или не активна");
                }
                throw new ResumeNotFoundException("Не найдено совпдаение Юзера " + user.getEmail() + " с юзером указанным в резюме");
            }
            throw new ResumeNotFoundException("Юзер " + user.getEmail() + " не найден среди соискателей");
        }
        throw new ResumeNotFoundException("Резюме с айди " + resumeId + " не найдено в системе для отклика на вакансию");
    }
}
