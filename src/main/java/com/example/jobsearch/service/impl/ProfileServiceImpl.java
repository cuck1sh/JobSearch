package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.user.ProfileDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.ProfileService;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final RespondedApplicantsService respondedApplicantsService;

    @Override
    public void getProfile(String email, Pageable pageable, String filter, Model model) {
        UserDto user = userService.getUserByEmail(email);
        putProfileInModel(user, pageable, filter, model);
        model.addAttribute("url", "/users/profile/" + email);
    }

    @Override
    public void getProfile(Pageable pageable, String filter, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            UserDto user = userService.getUserByEmail(auth.getName());
            putProfileInModel(user, pageable, filter, model);
            model.addAttribute("url", "/users/profile");
        } else {
            throw new UserNotFoundException("Не найден пользователь для профиля");
        }
    }

    private void putProfileInModel(UserDto user, Pageable pageable, String filter, Model model) {
        model.addAttribute("user", getProfileDto(user));

        if (userService.isEmployee(user.getEmail())) {
            if (resumeService.isUsersResumesInSystem(user.getId())) {

                model.addAttribute("page", resumeService.getResumeMainItem(user.getId(), pageable));
                model.addAttribute("responses", respondedApplicantsService.getResponsesForEmployee(user.getId()));
                model.addAttribute("responsesQuantity", respondedApplicantsService.getResponsesForEmployee(user.getId()).size());
            } else {
                model.addAttribute("responsesQuantity", 0);
            }
        } else {
            if (vacancyService.isUsersVacanciesInSystem(user.getId())) {
                model.addAttribute("page", vacancyService.getVacancyMainItem(user.getId(), pageable));
            }
        }

        String sort;
        StringBuilder sb = new StringBuilder();
        pageable.getSort().forEach(e -> sb.append(e.getProperty()).append(",").append(e.getDirection()));
        sort = sb.toString();

        model.addAttribute("sort", sort);
        model.addAttribute("filter", filter);
    }

    private ProfileDto getProfileDto(UserDto user) {
        String userName = userService.isEmployee(user.getId()) ? String.join(" ", user.getName(), user.getSurname()) : user.getName();

        return ProfileDto.builder()
                .id(user.getId())
                .name(userName)
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    private ProfileDto getProfileDto(User user) {
        String userName = userService.isEmployee(user.getId()) ? String.join(" ", user.getName(), user.getSurname()) : user.getName();

        return ProfileDto.builder()
                .id(user.getId())
                .name(userName)
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public Page<ProfileDto> getProfiles(Pageable pageable, Model model) {
        Page<User> users = userService.getCompanies(pageable);
        List<ProfileDto> profiles = new ArrayList<>();

        users.forEach(e -> profiles.add(getProfileDto(e)));

        return new PageImpl<>(profiles, pageable, userService.countCompanies());
    }
}
