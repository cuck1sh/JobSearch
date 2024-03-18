package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.EducationInfoDao;
import com.example.jobsearch.dto.EducationInfoDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.EducationInfo;
import com.example.jobsearch.service.EducationInfoService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationInfoServiceImpl implements EducationInfoService {

    private final EducationInfoDao educationInfoDao;

    @SneakyThrows
    @Override
    public EducationInfoDto getEducationInfoById(int id) {
        EducationInfo educationInfo = educationInfoDao.getEducationInfoById(id).orElseThrow(() -> new UserNotFoundException("Can not find EducationInfo with id: " + id));
        return EducationInfoDto.builder()
                .institution(educationInfo.getInstitution())
                .program(educationInfo.getProgram())
                .startDate(educationInfo.getStartDate())
                .endDate(educationInfo.getEndDate())
                .degree(educationInfo.getDegree())
                .build();
    }
}
