package com.example.jobsearch.controller;

import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public HttpStatus createVacancy(Authentication auth, @RequestBody @Valid VacancyDto vacancy) {
        return vacancyService.createVacancy(auth, vacancy);
    }

    @PostMapping("vacancies/change")
    public HttpStatus changeVacancy(Authentication auth, @RequestBody @Valid VacancyDto vacancy) {
        return vacancyService.changeVacancy(auth, vacancy);
    }

    @DeleteMapping("vacancies/{id}")
    public HttpStatus delete(Authentication auth, @PathVariable int id) {
        return vacancyService.delete(auth, id);
    }

    @GetMapping("vacancies")
    public ResponseEntity<List<ResumeDto>> getResumes() {
        return ResponseEntity.ok(resumeService.getResumes());
    }

    @GetMapping("resumes/category")
    public ResponseEntity<List<ResumeDto>> getResumesByCategory(@RequestParam(name = "name") String name) {
        List<ResumeDto> rdtos = resumeService.getResumesByCategory(name);
        return ResponseEntity.ok(rdtos);
    }

    @GetMapping("responses/users/{userId}")
    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForEmployer(@PathVariable int userId) {
        return ResponseEntity.ok(respondedApplicantsService.getResponsesForEmployer(userId));
    }

    @GetMapping("responses/vacancies/{vacancyId}")
    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForVacancy(@PathVariable int vacancyId) {
        return ResponseEntity.ok(respondedApplicantsService.getResponsesForVacancy(vacancyId));
    }


    // Проверить способ написания
    @PostMapping("employee")
    public ResponseEntity<List<UserDto>> getEmployee(@RequestBody String name, @RequestBody String surname, @RequestBody String email) {
        List<UserDto> users = userService.getEmployee(name, surname, email);
        return ResponseEntity.ok(users);
    }

}
