package com.example.jobsearch.controller;

import com.example.jobsearch.dto.RespondMessengerDto;
import com.example.jobsearch.dto.resume.InputContactInfoDto;
import com.example.jobsearch.dto.resume.InputResumeDto;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.RespondedApplicantsService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final ResumeService resumeService;
    private final CategoryService categoryService;
    private final RespondedApplicantsService respondedApplicantsService;

    @GetMapping("resumes/{id}")
    public String getResume(@PathVariable int id, Model model) {
        resumeService.getResume(id, model);
        return "employee/resume";
    }

    @GetMapping("resumes/add")
    public String addResume(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "employee/createResumeTemplate";
    }

    @PostMapping("resumes/add")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String makeResume(InputResumeDto resumeDto) {
        resumeService.createResume(resumeDto);
        return "redirect:/users/profile";
    }

    @GetMapping("resumes/update/{resumeId}")
    public String updateResume(Model model, @PathVariable int resumeId) {
        model.addAttribute("resume", resumeService.getResumeById(resumeId));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "employee/updateResumeTemplate";
    }

    @PostMapping("resumes/update")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String remakeResume(InputResumeDto resumeDto, InputContactInfoDto contacts, Model model) {
        resumeService.changeResume(resumeDto, contacts);
        return "redirect:/users/profile";
    }

    @GetMapping("resume/add/workExp/{resumeId}")
    public String createWorkExp(Model model, @PathVariable int resumeId) {
        model.addAttribute("resumeId", resumeId);
        return "employee/createWorkExpTemplate";
    }


    @GetMapping("resume/add/education/{resumeId}")
    public String createEducation(Model model, @PathVariable int resumeId) {
        model.addAttribute("resumeId", resumeId);
        return "employee/createEducationTemplate";
    }

    @GetMapping("resumes/{resumeId}/vacancy/{vacancyId}")
    public String getMessenger(@PathVariable int resumeId, @PathVariable int vacancyId, Model model) {
        RespondMessengerDto messengerDto = respondedApplicantsService.getRespondMessenger(FileUtil.TEST_RESUME_ID, FileUtil.TEST_VACANCY_ID);
        model.addAttribute("messenger", messengerDto);
        return "pages/messenger";
    }

}
