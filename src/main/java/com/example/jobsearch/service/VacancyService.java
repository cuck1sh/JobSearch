package com.example.jobsearch.service;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;

import java.util.List;

public interface VacancyService {

    VacancyDto getVacancyById(int id) throws UserNotFoundException;
    List<VacancyDto> getVacancies();
    List<VacancyDto> getVacanciesByCategory(String category);
}
