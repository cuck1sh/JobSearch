package com.example.jobsearch.service;

import com.example.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> getResumesByCategory(String category);
}
