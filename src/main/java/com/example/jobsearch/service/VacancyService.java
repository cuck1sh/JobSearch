package com.example.jobsearch.service;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.Vacancy;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface VacancyService {

    VacancyDto getVacancyById(int id) throws UserNotFoundException;
    List<VacancyDto> getVacancies();

    HttpStatus createVacancy(VacancyDto vacancy);

    Boolean isVacancyInSystem(int id);

    HttpStatus changeVacancyName(int id, String name);

    HttpStatus changeVacancyDescription(int id, String description);

    HttpStatus changeVacancyCategory(int id, String category);

    HttpStatus changeVacancySalary(int id, Double salary);

    HttpStatus changeVacancyExp(int id, int expFrom, int expTo);

    HttpStatus changeVacancyActive(int id, Boolean isActive);

    HttpStatus delete(int id);

    List<VacancyDto> getAllVacanciesByUser(int userId);
    List<VacancyDto> getVacanciesByCategory(String category);

    List<VacancyDto> getVacanciesByCategoryAndUser(int userId, String category);

    List<Vacancy> getCompanyVacancies(int userId);

    List<Vacancy> getActiveVacancies(int userId);
}
