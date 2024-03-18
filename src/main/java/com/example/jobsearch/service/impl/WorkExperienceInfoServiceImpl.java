package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.WorkExperienceInfoDao;
import com.example.jobsearch.dto.WorkExperienceInfoDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.WorkExperienceInfo;
import com.example.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl implements WorkExperienceInfoService {

    private final WorkExperienceInfoDao workExperienceInfoDao;

    @SneakyThrows
    @Override
    public WorkExperienceInfoDto WorkExperienceInfoById(int id) {
        WorkExperienceInfo workExperienceInfo = workExperienceInfoDao.getWorkExperienceInfoById(id).orElseThrow(() -> new UserNotFoundException("Can not find WorkExperienceInfo with id: " + id));
        return WorkExperienceInfoDto.builder()
                .years(workExperienceInfo.getYears())
                .companyName(workExperienceInfo.getCompanyName())
                .position(workExperienceInfo.getPosition())
                .responsibilities(workExperienceInfo.getResponsibilities())
                .build();
    }
}
