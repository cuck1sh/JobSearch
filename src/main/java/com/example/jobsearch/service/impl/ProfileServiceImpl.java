package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.resume.ResumeDto;
import com.example.jobsearch.dto.user.ProfileDto;
import com.example.jobsearch.dto.user.UserAvatarFileDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.dto.vacancy.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.ProfileService;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

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
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public void getProfile(String email, Pageable pageable, String filter, Model model) {
        UserDto userProfile = userService.getUserByEmail(email);
        putProfileInModel(userProfile, pageable, filter, model);
        model.addAttribute("url", "/users/profile/" + email);
        getItemsForResponse(userProfile, model);
    }

    private void getItemsForResponse(UserDto userProfile, Model model) {
        UserDto authUser = authenticatedUserProvider.getAuthUser();
        List<ResumeDto> resumes;
        List<VacancyDto> vacancies;

        if (userService.isEmployee(userProfile.getId())) {
            resumes = resumeService.getResumesByUserId(userProfile.getId());
            vacancies = vacancyService.getAllVacanciesByCompany(authUser.getId());
        } else {
            resumes = resumeService.getResumesByUserId(authUser.getId());
            vacancies = vacancyService.getAllVacanciesByCompany(userProfile.getId());
        }

        model.addAttribute("resumes", resumes);
        model.addAttribute("vacancies", vacancies);
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
                model.addAttribute("responses", respondedApplicantsService.getResponsesForEmployee(user.getId()).reversed());
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

    @Override
    public void updateUser(UserDto userDto, MultipartFile file) {
        UserDto authUser = authenticatedUserProvider.getAuthUser();

        User user = User.builder()
                .id(authUser.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .phoneNumber(userDto.getPhoneNumber())
                .build();

        if (!userDto.getPassword().isBlank()) {
            user.setPassword(userDto.getPassword());
        }

        userService.updateUser(user);

        if (file.getOriginalFilename().length() != 0) {
            userService.uploadUserAvatar(UserAvatarFileDto.builder()
                    .file(file)
                    .userId(authUser.getId())
                    .build());
        }
    }
}
