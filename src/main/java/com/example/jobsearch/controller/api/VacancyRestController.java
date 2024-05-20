package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.vacancy.VacancyDto;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/vacancies")
@RequiredArgsConstructor
public class VacancyRestController {
    private final VacancyService vacancyService;

    @PostMapping("search")
    public List<VacancyDto> search(String text) {
        return vacancyService.searchVacancies(text);
    }

}
