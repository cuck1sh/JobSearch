package com.example.jobsearch.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/resumes")
@RequiredArgsConstructor
public class ResumeRestController {

    //TODO УДАЛИТь ЕСЛИ НЕ НУЖНО

//    private final ResumeService resumeService;

//    @GetMapping
//    public ResponseEntity<List<ResumeDto>> getResumes() {
//        return ResponseEntity.ok(resumeService.getResumes());
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<?> getResumeById(@PathVariable int id) {
//        try {
//            ResumeDto resume = resumeService.getResumeById(id);
//            return ResponseEntity.ok(resume);
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
}
