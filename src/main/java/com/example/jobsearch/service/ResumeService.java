package com.example.jobsearch.service;

import com.example.jobsearch.dto.resume.InputContactInfoDto;
import com.example.jobsearch.dto.resume.InputResumeDto;
import com.example.jobsearch.dto.resume.ResumeDto;
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

    void createResume(String userEmail, InputResumeDto resumeDto);
    Boolean isResumeInSystem(int id);
    Boolean isUsersResumesInSystem(int userId);
    HttpStatus deleteResumeById(Authentication auth, int id);
    List<ResumeDto> getActiveResumes();
    void changeResume(String userEmail, InputResumeDto resume, InputContactInfoDto contacts);
    void getResume(String userEmail, int id, Model model);

    List<ResumeDto> getResumesWithPaging(Integer page, Integer pageSize);

    Integer getResumesCount();
}
