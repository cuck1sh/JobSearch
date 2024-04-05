package com.example.jobsearch.controller;

import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final ResumeService resumeService;

    @GetMapping("resumes/{id}")
    public String getResume(Authentication auth, @PathVariable int id, Model model) {
        resumeService.getResume(auth, id, model);
        return "employee/resume";
    }


}
