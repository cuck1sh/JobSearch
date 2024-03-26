package com.example.jobsearch.controller;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<List<VacancyDto>> getVacancies() {
        return ResponseEntity.ok(vacancyService.getVacancies());
    }

    @GetMapping("{id}")
    public ResponseEntity<VacancyDto> getVacancyById(@PathVariable int id) {
        return ResponseEntity.ok(vacancyService.getVacancyById(id));
    }

    @GetMapping("all/{userId}")
    public ResponseEntity<List<VacancyDto>> getAllVacanciesByCompany(@PathVariable int userId) {
        return ResponseEntity.ok(vacancyService.getAllVacanciesByCompany(userId));
    }

    @GetMapping("{userId}/category")
    public ResponseEntity<List<VacancyDto>> getVacanciesByCategoryAndUser(@PathVariable int userId, @RequestParam(name = "name") String name) {
        return ResponseEntity.ok(vacancyService.getVacanciesByCategoryAndUser(userId, name));
    }

}
