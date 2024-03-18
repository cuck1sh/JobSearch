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
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final RespondedApplicantsService respondedApplicantsService;
    private final UserService userService;

    @PostMapping("resumes")
    public HttpStatus createResume(@RequestBody ResumeDto resume) {
        return resumeService.createResume(resume);
    }

    @PostMapping("resumes/{id}/name")
    public HttpStatus changeName(@PathVariable int id, String name) {
        return resumeService.changeResumeName(id, name);
    }

    @PostMapping("resumes/{id}/category")
    public HttpStatus changeCategory(@PathVariable int id, String category) {
        return resumeService.changeResumeCategory(id, category);
    }

    @PostMapping("resumes/{id}/salary")
    public HttpStatus changeSalary(@PathVariable int id, Double salary) {
        return resumeService.changeResumeSalary(id, salary);
    }

    @PostMapping("resumes/{id}/active")
    public HttpStatus changeActive(@PathVariable int id, Boolean isActive) {
        return resumeService.changeResumeActive(id, isActive);
    }

    @DeleteMapping("resumes/{id}")
    public HttpStatus deleteResumeById(@PathVariable int id) {
        return resumeService.deleteResumeById(id);
    }

    @GetMapping("vacancies")
    public ResponseEntity<List<VacancyDto>> getVacancies() {
        return ResponseEntity.ok(vacancyService.getVacancies());
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
