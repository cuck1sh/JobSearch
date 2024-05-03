package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.EducationInfoDto;
import com.example.jobsearch.model.EducationInfo;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.repository.EducationInfoRepository;
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

    private final EducationInfoRepository educationInfoRepository;

    @Override
    public Boolean isResumeExist(int resumeId) {
        return educationInfoRepository.existsByResumeId(resumeId);
    }

    @Override
    public List<EducationInfoDto> getEducationInfoById(int resumeId) {
        if (isResumeExist(resumeId)) {
            List<EducationInfo> educationInfo = educationInfoRepository.findAllByResumeId(resumeId);
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
    public void createEducationInfo(List<EducationInfoDto> educationInfoDtos, Integer resumeId) {
        if (!educationInfoDtos.isEmpty()) {
            List<EducationInfo> educationInfos = new ArrayList<>();
            educationInfoDtos.forEach(e -> educationInfos.add(EducationInfo.builder()
                    .resume(Resume.builder().id(resumeId).build())
                    .institution(e.getInstitution())
                    .program(e.getProgram())
                    .startDate(e.getStartDate())
                    .endDate(e.getEndDate())
                    .degree(e.getDegree())
                    .build()));

            educationInfoRepository.saveAll(educationInfos);
        }
    }

    @Override
    public void changeEducationInfo(List<EducationInfoDto> educationInfoDtos, Integer resumeId) {
        if (!educationInfoDtos.isEmpty()) {
            educationInfoDtos.forEach(e -> educationInfoRepository.updateBy(e.getInstitution(),
                    e.getProgram(),
                    e.getStartDate(),
                    e.getEndDate(),
                    e.getDegree(),
                    e.getId()));
        }
    }
}
