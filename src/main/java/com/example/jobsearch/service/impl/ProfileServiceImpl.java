package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.resume.ResumeDto;
import com.example.jobsearch.dto.user.ProfileDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.dto.user.UserMainItem;
import com.example.jobsearch.service.ProfileService;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final RespondedApplicantsService respondedApplicantsService;

    @Override
    public void getProfile(String userAuth, Model model) {
        UserDto user = userService.getUserByEmail(userAuth);
        String userName = userService.isEmployee(user.getId()) ? String.join(" ", user.getName(), user.getSurname()) : user.getName();

        ProfileDto profileDto = ProfileDto.builder()
                .id(user.getId())
                .name(userName)
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();

        model.addAttribute("user", profileDto);

        if (userService.isEmployee(userAuth)) {
            if (resumeService.isUsersResumesInSystem(user.getId())) {
                List<ResumeDto> resumes = resumeService.getResumesByUserId(user.getId());
                List<UserMainItem> resumeDtos = new ArrayList<>();
                resumes.forEach(e -> resumeDtos.add(UserMainItem.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .timestamp(e.getUpdateTime())
                        .build()));
                model.addAttribute("userMainItems", resumeDtos.reversed());
                model.addAttribute("responsesQuantity", respondedApplicantsService.getResponsesForEmployee(user.getId()).size());
            } else {
                model.addAttribute("responsesQuantity", 0);
            }
        } else {
            if (vacancyService.isUsersVacanciesInSystem(user.getId())) {
                var vacancies = vacancyService.getAllVacanciesByCompany(user.getId());
                List<UserMainItem> vacanciesDtos = new ArrayList<>();
                vacancies.forEach(e -> vacanciesDtos.add(UserMainItem.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .timestamp(e.getUpdateTime())
                        .build()));
                model.addAttribute("userMainItems", vacanciesDtos.reversed());
            }
        }
    }
}
