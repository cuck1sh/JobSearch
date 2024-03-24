package com.example.jobsearch.service;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.Vacancy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {

    VacancyDto getVacancyById(int id) throws UserNotFoundException;
    List<VacancyDto> getVacancies();
    Boolean isVacancyInSystem(int id);
    Boolean isVacancyActive(int vacancyId);

    HttpStatus createVacancy(Authentication auth, VacancyDto vacancy);

    HttpStatus changeVacancy(Authentication auth, VacancyDto vacancy);

    HttpStatus delete(Authentication auth, int id);
    List<VacancyDto> getAllVacanciesByCompany(int userId);
    List<VacancyDto> getVacanciesByCategory(String category);
    List<VacancyDto> getVacanciesByCategoryAndUser(int userId, String category);
    List<Vacancy> getActiveVacancies(int userId);
}
