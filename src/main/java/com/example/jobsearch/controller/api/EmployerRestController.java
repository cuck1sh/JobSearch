package com.example.jobsearch.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/employer")
@RequiredArgsConstructor
public class EmployerRestController {

    //TODO УДАЛИТь ЕСЛИ НЕ НУЖНО

//    private final VacancyService vacancyService;
//    private final ResumeService resumeService;
//    private final RespondedApplicantsService respondedApplicantsService;
//    private final UserService userService;
//
//    @DeleteMapping("vacancies/{id}")
//    public HttpStatus delete(Authentication auth, @PathVariable int id) {
//        return vacancyService.delete(auth, id);
//    }
//
//    @GetMapping("resumes")
//    public ResponseEntity<List<ResumeDto>> getResumes() {
//        return ResponseEntity.ok(resumeService.getActiveResumes());
//    }
//
//    @GetMapping("resumes/category")
//    public ResponseEntity<List<ResumeDto>> getResumesByCategory(@RequestParam(name = "name") String name) {
//        List<ResumeDto> rdtos = resumeService.getResumesByCategory(name);
//        return ResponseEntity.ok(rdtos);
//    }
//
//    @GetMapping("responses/users/{userId}")
//    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForEmployer(@PathVariable int userId) {
//        return ResponseEntity.ok(respondedApplicantsService.getResponsesForEmployer(userId));
//    }
//
//    @GetMapping("responses/vacancies/{vacancyId}")
//    public ResponseEntity<List<RespondedApplicantsDto>> getResponsesForVacancy(@PathVariable int vacancyId) {
//        return ResponseEntity.ok(respondedApplicantsService.getResponsesForVacancy(vacancyId));
//    }
//
//
//    @PostMapping("employee")
//    public ResponseEntity<List<UserDto>> getEmployee(@RequestBody @Valid EmployeeFindDto employee) {
//        List<UserDto> users = userService.getEmployee(employee);
//        return ResponseEntity.ok(users);
//    }

}
