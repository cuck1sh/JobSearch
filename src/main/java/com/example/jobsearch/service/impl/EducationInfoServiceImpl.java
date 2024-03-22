package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.EducationInfoDao;
import com.example.jobsearch.dto.EducationInfoDto;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.model.EducationInfo;
import com.example.jobsearch.service.EducationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationInfoServiceImpl implements EducationInfoService {

    private final EducationInfoDao educationInfoDao;

    @Override
    public Boolean isResumeExist(int resumeId) {
        return educationInfoDao.isResumeExist(resumeId);
    }

    @Override
    public List<EducationInfoDto> getEducationInfoById(int resumeId) {
        if (isResumeExist(resumeId)) {
            List<EducationInfo> educationInfo = educationInfoDao.getEducationInfoById(resumeId);
            List<EducationInfoDto> educationInfoDtos = new ArrayList<>();
            educationInfo.forEach(e -> educationInfoDtos.add(EducationInfoDto.builder()
                    .institution(e.getInstitution())
                    .program(e.getProgram())
                    .startDate(e.getStartDate())
                    .endDate(e.getEndDate())
                    .degree(e.getDegree())
                    .build()));

            return educationInfoDtos;
        }
        throw new ResumeNotFoundException("Резюме с айди " + resumeId + " не найдено в системе");
    }

    @Override
    public void createEducationInfo(List<EducationInfoDto> educationInfoDtos, int newResumeKey) {
        if (!educationInfoDtos.isEmpty()) {
            List<EducationInfo> educationInfos = new ArrayList<>();
            educationInfoDtos.forEach(e -> educationInfos.add(EducationInfo.builder()
                    .resumeId(newResumeKey)
                    .institution(e.getInstitution())
                    .program(e.getProgram())
                    .startDate(e.getStartDate())
                    .endDate(e.getEndDate())
                    .degree(e.getDegree())
                    .build()));

            educationInfos.forEach(educationInfoDao::createEducationInfo);
        }
    }

    @Override
    public void changeEducationInfo(List<EducationInfoDto> educationInfoDtos, Integer resumeId) {
        if (!educationInfoDtos.isEmpty()) {
            List<EducationInfo> educationInfos = new ArrayList<>();
            educationInfoDtos.forEach(e -> educationInfos.add(EducationInfo.builder()
                    .resumeId(resumeId)
                    .institution(e.getInstitution())
                    .program(e.getProgram())
                    .startDate(e.getStartDate())
                    .endDate(e.getEndDate())
                    .degree(e.getDegree())
                    .build()));

            educationInfos.forEach(educationInfoDao::changeEducationInfo);
        }
    }
}
