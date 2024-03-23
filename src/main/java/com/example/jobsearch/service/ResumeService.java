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
    HttpStatus createResume(int userId, ResumeDto resume);
    Boolean isResumeInSystem(int id);
    HttpStatus deleteResumeById(int userId, int id);
    List<ResumeDto> getActiveResumes(int userId);
    HttpStatus changeResume(int userId, ResumeDto resume);

}
