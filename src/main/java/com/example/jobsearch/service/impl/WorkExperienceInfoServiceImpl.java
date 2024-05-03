package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.WorkExperienceInfoDto;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.WorkExperienceInfo;
import com.example.jobsearch.repository.WorkExperienceInfoRepository;
import com.example.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl implements WorkExperienceInfoService {

    private final WorkExperienceInfoRepository workExperienceInfoRepository;
    private static final String DEFAULT_VALUE = "undefined";

    @Override
    public Boolean isResumeExist(int resumeId) {
        return workExperienceInfoRepository.existsByResumeId(resumeId);
    }

    @Override
    public List<WorkExperienceInfoDto> WorkExperienceInfoById(int resumeId) {
        if (isResumeExist(resumeId)) {
            List<WorkExperienceInfo> workExperienceInfo = workExperienceInfoRepository.findAllByResumeId(resumeId);

            List<WorkExperienceInfoDto> workExperienceInfoDtos = new ArrayList<>();
            workExperienceInfo.forEach(e -> workExperienceInfoDtos.add(WorkExperienceInfoDto.builder()
                    .id(e.getId())
                    .years(e.getYears())
                    .companyName(e.getCompanyName())
                    .position(e.getPosition())
                    .responsibilities(e.getResponsibilities())
                    .build()));

            return workExperienceInfoDtos;
        } else {
            log.error("Резюме с айди " + resumeId + " не найдено в системе для выдачи информации об опыте работы");
            return null;
        }
    }

    @Override
    public void createWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfoDtos, Integer resumeId) {
        if (!workExperienceInfoDtos.isEmpty()) {
            List<WorkExperienceInfo> workExperienceInfos = new ArrayList<>();
            workExperienceInfoDtos.forEach(e -> workExperienceInfos.add(WorkExperienceInfo.builder()
                    .resume(Resume.builder().id(resumeId).build())
                    .years(e.getYears())
                    .companyName(e.getCompanyName())
                    .position(e.getPosition())
                    .responsibilities(e.getResponsibilities())
                    .build()));

            workExperienceInfoRepository.saveAll(workExperienceInfos);
        }
    }

    @Override
    public void changeWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfoDtos, Integer resumeId) {
        if (!workExperienceInfoDtos.isEmpty()) {
            workExperienceInfoDtos.forEach(e -> workExperienceInfoRepository.updateBy(e.getYears(),
                    e.getCompanyName(),
                    e.getPosition(),
                    e.getResponsibilities(),
                    e.getId()));
        }
    }
}
