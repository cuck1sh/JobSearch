package com.example.jobsearch.controller;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getVacancyById(@PathVariable int id) {
        try {
            VacancyDto vacancy = vacancyService.getVacancyById(id);
            return ResponseEntity.ok(vacancy);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("all/{userId}")
    public ResponseEntity<?> getAllVacanciesByCompany(@PathVariable int userId) {
        List<VacancyDto> vacancies = vacancyService.getAllVacanciesByCompany(userId);
        if (vacancies != null) {
            return ResponseEntity.ok(vacancies);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find vacancies by user: " + userId);
    }

    @GetMapping("{userId}/category")
    public ResponseEntity<?> getVacanciesByCategoryAndUser(@PathVariable int userId, @RequestParam(name = "name") String name) {
        List<VacancyDto> vacancies = vacancyService.getVacanciesByCategoryAndUser(userId, name);
        if (vacancies != null) {
            return ResponseEntity.ok(vacancies);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find vacancies by user: " + userId);
    }

}
