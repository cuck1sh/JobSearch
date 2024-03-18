package com.example.jobsearch.controller;

import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("employer")
@RequiredArgsConstructor
public class EmployerController {

    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    private final RespondedApplicantsService respondedApplicantsService;
    private final UserService userService;

    @PostMapping("vacancies")
    public HttpStatus createVacancy(@RequestBody VacancyDto vacancy) {
        return vacancyService.createVacancy(vacancy);
    }

    @PostMapping("{id}/name")
    public HttpStatus changeVacancyName(@PathVariable int id, String name) {
        return vacancyService.changeVacancyName(id, name);
    }

    @PostMapping("{id}/description")
    public HttpStatus changeVacancyDescription(@PathVariable int id, String description) {
        return vacancyService.changeVacancyDescription(id, description);
    }

    @PostMapping("{id}/category")
    public HttpStatus changeVacancyCategory(@PathVariable int id, String category) {
        return vacancyService.changeVacancyCategory(id, category);
    }

    @PostMapping("{id}/salary")
    public HttpStatus changeVacancySalary(@PathVariable int id, Double salary) {
        return vacancyService.changeVacancySalary(id, salary);
    }

    @PostMapping("{id}/exp")
    public HttpStatus changeVacancyExp(@PathVariable int id, int expFrom, int expTo) {
        return vacancyService.changeVacancyExp(id, expFrom, expTo);
    }

    @PostMapping("{id}/active")
    public HttpStatus changeVacancyActive(@PathVariable int id, boolean isActive) {
        return vacancyService.changeVacancyActive(id, isActive);
    }

    @DeleteMapping("{id}")
    public HttpStatus delete(@PathVariable int id) {
        return vacancyService.delete(id);
    }

    @GetMapping("vacancies")
    public ResponseEntity<List<ResumeDto>> getResumes() {
        return ResponseEntity.ok(resumeService.getResumes());
    }

    @GetMapping("category")
    public ResponseEntity<List<ResumeDto>> getResumesByCategory(@RequestParam(name = "name") String name) {
        List<ResumeDto> rdtos = resumeService.getResumesByCategory(name);
        return ResponseEntity.ok(rdtos);
    }

    @GetMapping("vacancies/{vacancyId}")
    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForVacancy(@PathVariable int vacancyId) {
        return ResponseEntity.ok(respondedApplicantsService.getResponsesForVacancy(vacancyId));
    }

    @PostMapping("users")
    public ResponseEntity<List<UserDto>> getEmployee(String name, String surname) {
        List<UserDto> users = userService.getUsersEmployee(name, surname);
        return ResponseEntity.ok(users);
    }

}
