package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.WorkExperienceInfoDao;
import com.example.jobsearch.dto.WorkExperienceInfoDto;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.model.WorkExperienceInfo;
import com.example.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl implements WorkExperienceInfoService {

    private final WorkExperienceInfoDao workExperienceInfoDao;

    @Override
    public Boolean isResumeExist(int resumeId) {
        return workExperienceInfoDao.isResumeExist(resumeId);
    }

    @Override
    public List<WorkExperienceInfoDto> WorkExperienceInfoById(int resumeId) {
        if (isResumeExist(resumeId)) {
            List<WorkExperienceInfo> workExperienceInfo = workExperienceInfoDao.getWorkExperienceInfoByResumeId(resumeId);
            List<WorkExperienceInfoDto> workExperienceInfoDtos = new ArrayList<>();
            workExperienceInfo.forEach(e -> workExperienceInfoDtos.add(WorkExperienceInfoDto.builder()
                    .years(e.getYears())
                    .companyName(e.getCompanyName())
                    .position(e.getPosition())
                    .responsibilities(e.getResponsibilities())
                    .build()));

            return workExperienceInfoDtos;
        }
        throw new ResumeNotFoundException("Резюме с айди " + resumeId + " не найдено в системе");
    }

    @Override
    public void createWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfoDtos, Integer newResumeKey) {
        if (!workExperienceInfoDtos.isEmpty()) {
            List<WorkExperienceInfo> workExperienceInfos = new ArrayList<>();
            workExperienceInfoDtos.forEach(e -> workExperienceInfos.add(WorkExperienceInfo.builder()
                    .resumeId(newResumeKey)
                    .years(e.getYears())
                    .companyName(e.getCompanyName())
                    .position(e.getPosition())
                    .responsibilities(e.getResponsibilities())
                    .build()));

            workExperienceInfos.forEach(workExperienceInfoDao::createWorkExperienceInfo);
        }
    }

    @Override
    public void changeWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfoDtos, Integer resumeId) {
        if (!workExperienceInfoDtos.isEmpty()) {
            List<WorkExperienceInfo> workExperienceInfos = new ArrayList<>();
            workExperienceInfoDtos.forEach(e -> workExperienceInfos.add(WorkExperienceInfo.builder()
                    .resumeId(resumeId)
                    .years(e.getYears())
                    .companyName(e.getCompanyName())
                    .position(e.getPosition())
                    .responsibilities(e.getResponsibilities())
                    .build()));

            workExperienceInfos.forEach(workExperienceInfoDao::changeWorkExperienceInfo);
        }
    }
}
