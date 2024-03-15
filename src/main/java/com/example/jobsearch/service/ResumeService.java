package com.example.jobsearch.service;

import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.exception.UserNotFoundException;

import java.util.List;

public interface ResumeService {
    ResumeDto getResumeById(int id) throws UserNotFoundException;
    List<ResumeDto> getResumesByCategory(String category);

    List<ResumeDto> getResumesByUserEmail(String email);

}
