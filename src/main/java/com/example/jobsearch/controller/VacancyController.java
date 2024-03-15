package com.example.jobsearch.controller;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping("{id}")
    public ResponseEntity<?> getVacancyById(@PathVariable int id) {
        try {
            VacancyDto vacancy = vacancyService.getVacancyById(id);
            return ResponseEntity.ok(vacancy);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<VacancyDto>> getVacancies() {
        return ResponseEntity.ok(vacancyService.getVacancies());
    }

    @GetMapping("category")
    public ResponseEntity<List<VacancyDto>> getVacanciesByCategory(@RequestParam(name = "name") String category) {
        return ResponseEntity.ok(vacancyService.getVacanciesByCategory(category));
    }
}
