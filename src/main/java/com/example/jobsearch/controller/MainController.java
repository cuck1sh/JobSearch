package com.example.jobsearch.controller;

import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class MainController {
    private final VacancyService vacancyService;

    @GetMapping
    public String getMainPage(Model model) {
        model.addAttribute("vacancies", vacancyService.getActiveVacancies());
        return "main";
    }
}
