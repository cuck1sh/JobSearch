package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.WorkExperienceInfoDto;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.WorkExperienceInfo;
import com.example.jobsearch.repository.WorkExperienceInfoRepository;
import com.example.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl implements WorkExperienceInfoService {

    private final WorkExperienceInfoRepository workExperienceInfoRepository;

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
    public void changeWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfoDtos) {
        if (!workExperienceInfoDtos.isEmpty()) {
            workExperienceInfoDtos.forEach(e -> workExperienceInfoRepository.updateBy(e.getYears(),
                    e.getCompanyName(),
                    e.getPosition(),
                    e.getResponsibilities(),
                    e.getId()));
        }
    }

    @Override
    public void createWorkExperienceInfo(WorkExperienceInfoDto workExperienceInfoDto, Integer resumeId) {
        workExperienceInfoRepository.save(WorkExperienceInfo.builder()
                .resume(Resume.builder().id(resumeId).build())
                .years(workExperienceInfoDto.getYears())
                .companyName(workExperienceInfoDto.getCompanyName())
                .position(workExperienceInfoDto.getPosition())
                .responsibilities(workExperienceInfoDto.getResponsibilities())
                .build());
    }

    @Override
    public void changeWorkExperienceInfo(WorkExperienceInfoDto workExperienceInfos) {
        workExperienceInfoRepository.updateBy(workExperienceInfos.getYears(),
                workExperienceInfos.getCompanyName(),
                workExperienceInfos.getPosition(),
                workExperienceInfos.getResponsibilities(),
                workExperienceInfos.getId()
        );
    }

    @Override
    public HttpStatus deleteWorkExp(int workExpId) {
        workExperienceInfoRepository.deleteById(workExpId);
        return HttpStatus.OK;
    }
}
