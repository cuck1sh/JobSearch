package com.example.jobsearch.controller;

import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.VacancyService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
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
    private final RespondedApplicantsService respondedApplicantsService;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final ResumeService resumeService;

    @GetMapping
    public String getMainPage(Model model,
                              @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(name = "filter", required = false, defaultValue = "none") String category
    ) {

        model.addAttribute("page", vacancyService.getVacanciesWithPaging(pageable, category));
        model.addAttribute("filter", category);
        model.addAttribute("url", "/");
        model.addAttribute("categories", categoryService.getAllCategories());

        String sort;
        StringBuilder sb = new StringBuilder();
        pageable.getSort().forEach(e -> sb.append(e.getProperty()).append(",").append(e.getDirection()));
        sort = sb.toString();

        model.addAttribute("sort", sort);

        return "main/index";
    }

    @GetMapping("vacancies/{id}")
    public String getVacancy(@PathVariable int id, Model model) {
        respondedApplicantsService.getVacancy(id, model);
        return "employer/vacancy";
    }

    @GetMapping("resumes/{id}")
    public String getResume(@PathVariable int id, Model model) {
        return respondedApplicantsService.getResume(id, model);
    }

    @GetMapping("search")
    public String search() {
        return "pages/search";
    }
}
