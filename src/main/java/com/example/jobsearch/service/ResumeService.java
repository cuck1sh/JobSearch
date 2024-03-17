package com.example.jobsearch.service;

import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ResumeService {

    List<ResumeDto> getResumes();
    ResumeDto getResumeById(int id) throws UserNotFoundException;
    List<ResumeDto> getResumesByCategory(String category);
    List<ResumeDto> getResumesByUserEmail(String email);
    void createResume(ResumeDto resume);
    Boolean isResumeInSystem(int id);
    HttpStatus changeResumeName(int id, String name);
    HttpStatus changeResumeCategory(int id, String category);
    HttpStatus changeResumeSalary(int id, Double salary);
    HttpStatus changeResumeActive(int id, Boolean status);
    HttpStatus deleteResumeById(int id);

    List<ResumeDto> getActiveResumes(int userId);

}
