package com.example.jobsearch.controller;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class MainController {
    private final VacancyService vacancyService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping
    public String getMainPage(Model model,
                              @RequestParam(name = "page", defaultValue = "0") Integer page,
                              @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                              @RequestParam(name = "filter", defaultValue = "none") String category
    ) {
        model.addAttribute("vacancies", vacancyService.getVacanciesWithPaging(page, pageSize, category));
        model.addAttribute("page", page);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "main";
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String createUser(UserDto user, @RequestParam(name = "file") MultipartFile file) {
        userService.createUser(user, file);
        return "redirect:/";
    }

    @GetMapping("vacancies/{id}")
    public String getResume(@PathVariable int id, Model model) {
        vacancyService.getVacancy(id, model);
        return "employer/vacancy";
    }
}
