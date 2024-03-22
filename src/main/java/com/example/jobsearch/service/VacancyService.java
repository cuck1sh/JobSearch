package com.example.jobsearch.service;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.Vacancy;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface VacancyService {

    VacancyDto getVacancyById(int id) throws UserNotFoundException;
    List<VacancyDto> getVacancies();

    HttpStatus createVacancy(int userId, VacancyDto vacancy);
    Boolean isVacancyInSystem(int id);
    HttpStatus delete(int userId, int id);
    List<VacancyDto> getAllVacanciesByCompany(int userId);
    List<VacancyDto> getVacanciesByCategory(String category);
    List<VacancyDto> getVacanciesByCategoryAndUser(int userId, String category);
    List<Vacancy> getActiveVacancies(int userId);
    HttpStatus changeVacancy(int userId, VacancyDto vacancy);
}
