package com.example.jobsearch.controller;

import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping("resumes/category")
    public ResponseEntity<List<ResumeDto>> getResumesByCategory(@RequestParam(name = "category") String category) {
        List<ResumeDto> rdtos = resumeService.getResumesByCategory(category);
        return ResponseEntity.ok(rdtos);
    }

    @GetMapping("resumes/user")
    public ResponseEntity<List<ResumeDto>> getResumesByUserEmail(@RequestParam(name = "email") String email) {
        List<ResumeDto> rdtos = resumeService.getResumesByUserEmail(email);
        return ResponseEntity.ok(rdtos);
    }
}
