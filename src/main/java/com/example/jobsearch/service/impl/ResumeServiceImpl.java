package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    @Override
    public ResumeDto getResumeById(int id) throws UserNotFoundException {
        Resume resume = resumeDao.getResumeById(id).orElseThrow(() -> new UserNotFoundException("Can not find resume with id: " + id));
        return ResumeDto.builder()
                .id(resume.getId())
                .userId(resume.getUserId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .build();
    }
    @Override
    public List<ResumeDto> getResumesByCategory(String category) {
        List<Resume> resumes = resumeDao.getResumesByCategory(category);
        return getResumeDtos(resumes);
    }

    @Override
    public List<ResumeDto> getResumesByUserEmail(String email) {
        List<Resume> resumes = resumeDao.getResumesByUserEmail(email);
        return getResumeDtos(resumes);
    }

    private List<ResumeDto> getResumeDtos(List<Resume> resumes) {
        List<ResumeDto> dtos = new ArrayList<>();
        resumes.forEach(e -> dtos.add(ResumeDto.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .name(e.getName())
                .categoryId(e.getCategoryId())
                .salary(e.getSalary())
                .isActive(e.getIsActive())
                .createdDate(e.getCreatedDate())
                .updateTime(e.getUpdateTime())
                .build()));
        return dtos;
    }
}
