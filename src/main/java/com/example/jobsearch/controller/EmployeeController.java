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
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final RespondedApplicantsService respondedApplicantsService;
    private final UserService userService;

    @PostMapping("{userId}/resumes")
    public HttpStatus createResume(@PathVariable int userId, @RequestBody @Valid ResumeDto resume) {
        return resumeService.createResume(userId, resume);
    }


    @PostMapping("{userId}/resumes/change")
    public HttpStatus changeResume(@PathVariable int userId, @RequestBody @Valid ResumeDto resume) {
        return resumeService.changeResume(userId, resume);
    }

    @DeleteMapping("{userId}/resumes/{id}")
    public HttpStatus deleteResumeById(@PathVariable int userId, @PathVariable int id) {
        return resumeService.deleteResumeById(userId, id);
    }

    @GetMapping("vacancies")
    public ResponseEntity<List<VacancyDto>> getVacancies() {
        return ResponseEntity.ok(vacancyService.getVacancies());
    }

    @GetMapping("vacancies/active/{id}")
    public ResponseEntity<Boolean> isVacancyActive(@PathVariable int id) {
        return ResponseEntity.ok(vacancyService.isVacancyActive(id));
    }

    @PostMapping("{userId}/vacancies/{vacancyId}")
    public HttpStatus sendResponse(@PathVariable int userId, @PathVariable int vacancyId, @RequestBody Integer resumeId) {
        return respondedApplicantsService.sendResponseForVacancy(userId, vacancyId, resumeId);
    }

    @GetMapping("vacancies/category")
    public ResponseEntity<List<VacancyDto>> getVacanciesByCategory(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(vacancyService.getVacanciesByCategory(name));
    }

    @GetMapping("responses/users/{userId}")
    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForUser(@PathVariable int userId) {
        return ResponseEntity.ok(respondedApplicantsService.getResponsesForEmployee(userId));
    }

    @GetMapping("responses/resumes/{resumeId}")
    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForResume(@PathVariable int resumeId) {
        return ResponseEntity.ok(respondedApplicantsService.getResponsesForResume(resumeId));
    }

    @PostMapping("employer")
    public ResponseEntity<List<UserDto>> getEmployer(String name) {
        return ResponseEntity.ok(userService.getEmployer(name));

    }


}
