package com.example.jobsearch.controller;

import com.example.jobsearch.dto.vacancy.InputVacancyDto;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.ProfileService;
import com.example.jobsearch.service.VacancyService;
import com.example.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("employer")
@RequiredArgsConstructor
public class EmployerController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;
    private final ProfileService profileService;

    @GetMapping("vacancies/add")
    public String addVacancy(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "employer/createVacancyTemplate";
    }

    @PostMapping("vacancies/add")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String makeVacancy(InputVacancyDto vacancyDto, Model model) {
        vacancyService.createVacancy(FileUtil.TEST_USER_AUTH, vacancyDto);
        profileService.getProfile(FileUtil.TEST_USER_AUTH, model);
        return "user/profile";
    }

    @GetMapping("vacancies/update/{vacancyId}")
    public String updateVacancy(Model model, @PathVariable int vacancyId) {
        model.addAttribute("vacancy", vacancyService.getVacancyById(vacancyId));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "employer/updateVacancyTemplate";
    }

    @PostMapping("vacancies/update")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String makeUpdate(InputVacancyDto inputVacancyDto, Model model) {
        vacancyService.changeVacancy(FileUtil.TEST_USER_AUTH, inputVacancyDto);
        profileService.getProfile(FileUtil.TEST_USER_AUTH, model);
        return "user/profile";
    }

}
