package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.service.ResumeService;
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
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping
    public ResponseEntity<List<ResumeDto>> getResumes() {
        return ResponseEntity.ok(resumeService.getResumes());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getResumeById(@PathVariable int id) {
        try {
            ResumeDto resume = resumeService.getResumeById(id);
            return ResponseEntity.ok(resume);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("category")
    public ResponseEntity<List<ResumeDto>> getResumesByCategory(@RequestParam(name = "name") String name) {
        List<ResumeDto> rdtos = resumeService.getResumesByCategory(name);
        return ResponseEntity.ok(rdtos);
    }

    @GetMapping("user")
    public ResponseEntity<List<ResumeDto>> getResumesByUserEmail(@RequestParam(name = "email") String email) {
        List<ResumeDto> rdtos = resumeService.getResumesByUserEmail(email);
        return ResponseEntity.ok(rdtos);
    }


    @PostMapping
    public HttpStatus createResume(@RequestBody ResumeDto resume) {
        resumeService.createResume(resume);
        return HttpStatus.OK;
    }

    @PostMapping("{id}/name")
    public HttpStatus changeName(@PathVariable int id, String name) {
        return resumeService.changeResumeName(id, name);
    }

    @PostMapping("{id}/category")
    public HttpStatus changeCategory(@PathVariable int id, String category) {
        return resumeService.changeResumeCategory(id, category);
    }

    @PostMapping("{id}/salary")
    public HttpStatus changeSalary(@PathVariable int id, Double salary) {
        return resumeService.changeResumeSalary(id, salary);
    }

    @PostMapping("{id}/active")
    public HttpStatus changeActive(@PathVariable int id, Boolean isActive) {
        return resumeService.changeResumeActive(id, isActive);
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteResumeById(@PathVariable int id) {
        return resumeService.deleteResumeById(id);
    }
}
