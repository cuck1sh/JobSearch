package com.example.jobsearch.controller.api;

import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.EducationInfoService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/employee")
@RequiredArgsConstructor
public class EmployeeRestController {
    private final WorkExperienceInfoService workExperienceInfoService;
    private final EducationInfoService educationInfoService;
    private final CategoryService categoryService;
    private final ResumeService resumeService;

    @DeleteMapping("resume/workExp/{workExpId}")
    public HttpStatus deleteWorkExp(@PathVariable int workExpId) {
        return workExperienceInfoService.deleteWorkExp(workExpId);
    }

    @DeleteMapping("resume/eduInfo/{eduInfoId}")
    public HttpStatus deleteEduInfo(@PathVariable int eduInfoId) {
        return educationInfoService.deleteEduInfo(eduInfoId);
    }
}
