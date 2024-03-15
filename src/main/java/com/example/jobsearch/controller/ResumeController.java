package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

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
    public ResponseEntity<List<ResumeDto>> getResumesByCategory(@RequestParam(name = "category") String category) {
        List<ResumeDto> rdtos = resumeService.getResumesByCategory(category);
        return ResponseEntity.ok(rdtos);
    }

    @GetMapping("user")
    public ResponseEntity<List<ResumeDto>> getResumesByUserEmail(@RequestParam(name = "email") String email) {
        List<ResumeDto> rdtos = resumeService.getResumesByUserEmail(email);
        return ResponseEntity.ok(rdtos);
    }

    @PostMapping("add")
    public HttpStatus addNewResume(ResumeDto resume) {
        resumeService.createResume(resume);
        return HttpStatus.OK;
    }

    @GetMapping("delete/{id}")
    public HttpStatus deleteResumeById(@PathVariable int id) {
        resumeService.deleteResumeById(id);
        return HttpStatus.OK;
    }
}
