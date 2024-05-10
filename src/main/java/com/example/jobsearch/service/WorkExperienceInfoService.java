package com.example.jobsearch.service;

import com.example.jobsearch.dto.WorkExperienceInfoDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface WorkExperienceInfoService {

    Boolean isResumeExist(int resumeId);

    List<WorkExperienceInfoDto> WorkExperienceInfoById(int id);

    void createWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfoDto, Integer resumeId);

    void createWorkExperienceInfo(WorkExperienceInfoDto workExperienceInfoDto, Integer resumeId);

    void changeWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfos);

    void changeWorkExperienceInfo(WorkExperienceInfoDto workExperienceInfos);

    HttpStatus deleteWorkExp(int workExpId);
}
