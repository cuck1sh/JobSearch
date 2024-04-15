package com.example.jobsearch.service;

import com.example.jobsearch.dto.WorkExperienceInfoDto;

import java.util.List;

public interface WorkExperienceInfoService {

    Boolean isResumeExist(int resumeId);

    List<WorkExperienceInfoDto> WorkExperienceInfoById(int id);

    void createWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfoDto, Integer resumeId);

    void changeWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfos, Integer resumeId);
}
