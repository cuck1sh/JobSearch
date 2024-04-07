package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.RespondedApplicantsDto;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.dto.resume.InputResumeDto;
import com.example.jobsearch.dto.user.UserDto;
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
@RequestMapping("api/employee")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final RespondedApplicantsService respondedApplicantsService;
    private final UserService userService;

    @PostMapping("resumes")
    public HttpStatus createResume(String userEmail, @RequestBody @Valid InputResumeDto resume) {
        resumeService.createResume(userEmail);
        return HttpStatus.OK;
    }

    @DeleteMapping("resumes/{id}")
    public HttpStatus deleteResumeById(Authentication auth, @PathVariable int id) {
        return resumeService.deleteResumeById(auth, id);
    }

    @GetMapping("vacancies")
    public ResponseEntity<List<VacancyDto>> getVacancies() {
        return ResponseEntity.ok(vacancyService.getActiveVacancies());
    }

    @GetMapping("vacancies/active/{id}")
    public ResponseEntity<Boolean> isVacancyActive(@PathVariable int id) {
        return ResponseEntity.ok(vacancyService.isVacancyActive(id));
    }

    @PostMapping("vacancies/{vacancyId}")
    public HttpStatus sendResponse(Authentication auth, @PathVariable int vacancyId, @RequestBody Integer resumeId) {
        return respondedApplicantsService.sendResponseForVacancy(auth, vacancyId, resumeId);
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
    public ResponseEntity<List<UserDto>> getEmployer(@RequestBody String name) {
        return ResponseEntity.ok(userService.getEmployer(name));
    }

}
