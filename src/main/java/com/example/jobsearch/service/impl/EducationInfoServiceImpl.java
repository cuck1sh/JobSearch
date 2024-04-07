package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.EducationInfoDao;
import com.example.jobsearch.dto.EducationInfoDto;
import com.example.jobsearch.model.EducationInfo;
import com.example.jobsearch.service.EducationInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
                    .id(e.getId())
                    .institution(e.getInstitution())
                    .program(e.getProgram())
                    .startDate(e.getStartDate())
                    .endDate(e.getEndDate())
                    .degree(e.getDegree())
                    .build()));

            return educationInfoDtos;
        } else {
            log.error("Резюме с айди " + resumeId + " не найдено в системе для выдачи информации об обучении");
            return null;
        }
    }

    @Override
    public void createEducationInfo(EducationInfoDto educationInfoDto) {
        EducationInfo educationInfo = EducationInfo.builder()
                .resumeId(educationInfoDto.getResumeId())
                .institution(educationInfoDto.getInstitution())
                .program(educationInfoDto.getProgram())
                .startDate(educationInfoDto.getStartDate())
                .endDate(educationInfoDto.getEndDate())
                .build();

        if (educationInfoDto.getDegree() == null) {
            educationInfo.setDegree(" ");
        } else {
            educationInfo.setDegree(educationInfoDto.getDegree());
        }

        educationInfoDao.createEducationInfo(educationInfo);
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
