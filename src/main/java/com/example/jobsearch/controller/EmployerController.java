package com.example.jobsearch.controller;

import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("employer")
@RequiredArgsConstructor
public class EmployerController {
    private final VacancyService vacancyService;

    @GetMapping("vacancies/{id}")
    public String getResume(Authentication auth, @PathVariable int id, Model model) {
        vacancyService.getVacancy(auth, id, model);
        return "employer/vacancy";
    }
}
