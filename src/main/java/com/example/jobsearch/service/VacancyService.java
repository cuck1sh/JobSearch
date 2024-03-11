package com.example.jobsearch.service;

import com.example.jobsearch.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getVacancies();
}
