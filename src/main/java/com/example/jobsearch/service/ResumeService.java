package com.example.jobsearch.service;

import com.example.jobsearch.dto.resume.InputResumeDto;
import com.example.jobsearch.dto.resume.ResumeDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.dto.user.UserMainItem;
import com.example.jobsearch.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;

public interface ResumeService {

    List<ResumeDto> getResumes();
    ResumeDto getResumeById(int id) throws UserNotFoundException;

    InputResumeDto getInputResumeById(int id) throws UserNotFoundException;
    List<ResumeDto> getResumesByCategory(String category);
    List<ResumeDto> getResumesByUserId(int id);
    Page<UserMainItem> getResumeMainItem(Integer userId, Pageable pageable);
    void createResume(InputResumeDto resumeDto);
    Boolean isResumeInSystem(int id);
    Boolean isUsersResumesInSystem(int userId);
    HttpStatus deleteResumeById(Authentication auth, int id);
    List<ResumeDto> getActiveResumes();

    void changeResume(InputResumeDto resume);
    void getResume(int id, Model model);

    Page<ResumeDto> getResumesWithPaging(Pageable pageable, String filter);
    Integer getResumesCount();

    UserDto getUserByResume(int resumeId);
}
