package com.example.jobsearch.service;

import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.exception.UserNotFoundException;

import java.util.List;

public interface ResumeService {
    ResumeDto getResumeById(int id) throws UserNotFoundException;
    List<ResumeDto> getResumesByCategory(String category);
    List<ResumeDto> getResumesByUserEmail(String email);

    List<ResumeDto> getActiveResumes(int id);

    List<ResumeDto> getNotActiveResumes(int id);

    void createResume(ResumeDto resume);

    void deleteResumeById(int id);

    void changeResumeName(int id, String name);

    void changeResumeCategory(int id, String category);

    void changeResumeSalary(int id, Double salary);

    void changeResumeActive(int id, Boolean status);

}
