package com.example.jobsearch.controller;

import com.example.jobsearch.dto.vacancy.InputVacancyDto;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.VacancyService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
@RequestMapping("employer")
@RequiredArgsConstructor
public class EmployerController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;
    private final ResumeService resumeService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @GetMapping("vacancies/add")
    public String addVacancy(Model model) {
        model.addAttribute("inputVacancyDto", new InputVacancyDto());
        model.addAttribute("categories", categoryService.getStringedCategories());
        return "employer/createVacancyTemplate";
    }

    @PostMapping("vacancies/add")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String makeVacancy(@Valid InputVacancyDto inputVacancyDto,
                              BindingResult bindingResult,
                              Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("inputVacancyDto", inputVacancyDto);
            model.addAttribute("categories", categoryService.getStringedCategories());
            return "employer/createVacancyTemplate";
        }
        if (inputVacancyDto.getExpFrom() != null && inputVacancyDto.getExpTo() != null) {
            if (inputVacancyDto.getExpFrom() > inputVacancyDto.getExpTo()) {
                model.addAttribute("inputVacancyDto", inputVacancyDto);
                model.addAttribute("categories", categoryService.getStringedCategories());
                model.addAttribute("expErr", true);
                return "employer/createVacancyTemplate";
            }
        }
        vacancyService.createVacancy(inputVacancyDto);
        return "redirect:/users/profile";
    }

    @GetMapping("vacancies/update/{vacancyId}")
    public String updateVacancy(@PathVariable int vacancyId, Model model) {
        model.addAttribute("inputVacancyDto", vacancyService.getInputVacancyById(vacancyId));
        model.addAttribute("categories", categoryService.getStringedCategories());
        return "employer/updateVacancyTemplate";
    }

    @PostMapping("vacancies/update")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String makeUpdate(@Valid InputVacancyDto inputVacancyDto,
                             BindingResult bindingResult,
                             Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("inputVacancyDto", inputVacancyDto);
            model.addAttribute("categories", categoryService.getStringedCategories());
            return "employer/updateVacancyTemplate";
        }
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

}
