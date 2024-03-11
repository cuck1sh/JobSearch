package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dto.ResumeDto;
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
