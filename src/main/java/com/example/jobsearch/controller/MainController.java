package com.example.jobsearch.controller;

import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class MainController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;

    @GetMapping
    public String getMainPage(Model model,
                              @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(name = "filter", required = false, defaultValue = "none") String category
    ) {

        model.addAttribute("page", vacancyService.getVacanciesWithPaging(pageable, category));
        model.addAttribute("url", "/");
        model.addAttribute("categories", categoryService.getAllCategories());
        return "main/index";
    }

    @GetMapping("vacancies/{id}")
    public String getResume(@PathVariable int id, Model model) {
        vacancyService.getVacancy(id, model);
        return "employer/vacancy";
    }

    @GetMapping("login")
    public String getTestLogin() {
        return "auth/login";
    }
}
