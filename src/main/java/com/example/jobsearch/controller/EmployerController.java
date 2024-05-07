package com.example.jobsearch.controller;

import com.example.jobsearch.dto.vacancy.InputVacancyDto;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("employer")
@RequiredArgsConstructor
public class EmployerController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;
    private final ResumeService resumeService;

    @GetMapping("vacancies/add")
    public String addVacancy(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "employer/createVacancyTemplate";
    }

    @PostMapping("vacancies/add")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String makeVacancy(InputVacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return "redirect:/users/profile";
    }

    @GetMapping("vacancies/update/{vacancyId}")
    public String updateVacancy(Model model, @PathVariable int vacancyId) {
        model.addAttribute("vacancy", vacancyService.getVacancyById(vacancyId));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "employer/updateVacancyTemplate";
    }

    @PostMapping("vacancies/update")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String makeUpdate(InputVacancyDto inputVacancyDto) {
        vacancyService.changeVacancy(inputVacancyDto);
        return "redirect:/users/profile";
    }

    @GetMapping("resumes")
    public String getMainPage(Model model,
                              @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(name = "filter", required = false, defaultValue = "none") String category
    ) {
        model.addAttribute("page", resumeService.getResumesWithPaging(pageable, category));
        model.addAttribute("filter", category);
        model.addAttribute("url", "/employer/resumes");
        model.addAttribute("categories", categoryService.getAllCategories());

        String sort;
        StringBuilder sb = new StringBuilder();
        pageable.getSort().forEach(e -> sb.append(e.getProperty()).append(",").append(e.getDirection()));
        sort = sb.toString();

        model.addAttribute("sort", sort);
        return "employer/resumes";
    }

    @GetMapping("resumes/{id}")
    public String getResume(@PathVariable int id, Model model) {
        model.addAttribute("resume", resumeService.getResumeById(id));
        return "employee/resume";
    }

}
