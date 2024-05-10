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

    //TODO УДАЛИТь ЕСЛИ НЕ НУЖНО

//    private final ResumeService resumeService;
//    private final VacancyService vacancyService;
//    private final RespondedApplicantsService respondedApplicantsService;
//    private final UserService userService;
//
//    @DeleteMapping("resumes/{id}")
//    public HttpStatus deleteResumeById(Authentication auth, @PathVariable int id) {
//        return resumeService.deleteResumeById(auth, id);
//    }
//
//    @GetMapping("vacancies")
//    public ResponseEntity<List<VacancyDto>> getVacancies() {
//        return ResponseEntity.ok(vacancyService.getActiveVacancies());
//    }
//
//    @GetMapping("vacancies/active/{id}")
//    public ResponseEntity<Boolean> isVacancyActive(@PathVariable int id) {
//        return ResponseEntity.ok(vacancyService.isVacancyActive(id));
//    }
//
//    @PostMapping("vacancies/{vacancyId}")
//    public HttpStatus sendResponse(Authentication auth, @PathVariable int vacancyId, @RequestBody Integer resumeId) {
//        return respondedApplicantsService.sendResponseForVacancy(auth, vacancyId, resumeId);
//    }
//
//    @GetMapping("vacancies/category")
//    public ResponseEntity<List<VacancyDto>> getVacanciesByCategory(@RequestParam(name = "name") String name) {
//        return ResponseEntity.ok(vacancyService.getVacanciesByCategory(name));
//    }
//
//    @GetMapping("responses/users/{userId}")
//    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForUser(@PathVariable int userId) {
//        return ResponseEntity.ok(respondedApplicantsService.getResponsesForEmployee(userId));
//    }
//
//    @GetMapping("responses/resumes/{resumeId}")
//    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForResume(@PathVariable int resumeId) {
//        return ResponseEntity.ok(respondedApplicantsService.getResponsesForResume(resumeId));
//    }
//
//    @PostMapping("employer")
//    public ResponseEntity<List<UserDto>> getEmployer(@RequestBody String name) {
//        return ResponseEntity.ok(userService.getEmployer(name));
//    }

}
