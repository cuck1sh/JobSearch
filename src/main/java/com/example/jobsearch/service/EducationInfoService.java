package com.example.jobsearch.service;

import com.example.jobsearch.dto.EducationInfoDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface EducationInfoService {
    Boolean isResumeExist(int resumeId);
    List<EducationInfoDto> getEducationInfoById(int resumeId);

    void createEducationInfo(List<EducationInfoDto> educationInfos, Integer resumeId);

    void createEducationInfo(EducationInfoDto educationInfos, Integer resumeId);

    void changeEducationInfo(List<EducationInfoDto> educationInfos);

    void changeEducationInfo(EducationInfoDto educationInfos);

    HttpStatus deleteEduInfo(int eduInfoId);
}
