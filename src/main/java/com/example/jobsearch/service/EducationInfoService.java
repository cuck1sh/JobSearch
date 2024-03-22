package com.example.jobsearch.service;

import com.example.jobsearch.dto.EducationInfoDto;

import java.util.List;

public interface EducationInfoService {

    Boolean isResumeExist(int resumeId);

    List<EducationInfoDto> getEducationInfoById(int resumeId);

    void createEducationInfo(List<EducationInfoDto> educationInfo, int newResumeKey);

    void changeEducationInfo(List<EducationInfoDto> educationInfos, Integer resumeId);
}
