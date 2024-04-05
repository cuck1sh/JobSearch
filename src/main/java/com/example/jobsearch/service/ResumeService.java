package com.example.jobsearch.service;

import com.example.jobsearch.dto.InputResumeDto;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;

public interface ResumeService {

    List<ResumeDto> getResumes();
    ResumeDto getResumeById(int id) throws UserNotFoundException;
    List<ResumeDto> getResumesByCategory(String category);

    List<ResumeDto> getResumesByUserId(int id);

    HttpStatus createResume(Authentication auth, InputResumeDto resume);
    Boolean isResumeInSystem(int id);
    HttpStatus deleteResumeById(Authentication auth, int id);

    List<ResumeDto> getActiveResumes();

    HttpStatus changeResume(Authentication auth, InputResumeDto resume);

    void getResume(Authentication auth, int id, Model model);
}
