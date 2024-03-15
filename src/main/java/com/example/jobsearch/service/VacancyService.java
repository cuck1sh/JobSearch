package com.example.jobsearch.service;

import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.Vacancy;

import java.util.List;

public interface VacancyService {

    VacancyDto getVacancyById(int id) throws UserNotFoundException;
    List<VacancyDto> getVacancies();
    List<VacancyDto> getVacanciesByCategory(String category);

    List<Vacancy> getCompanyVacancies(int userId);

    List<Vacancy> getActiveVacancies(int userId);

    void createVacancy(VacancyDto vacancy);

    void changeVacancyName(int id, String name);

    void changeVacancyDescription(int id, String description);

    void changeVacancyCategory(int id, String category);

    void changeVacancySalary(int id, Double salary);

    void changeVacancyExpFrom(int id, int expFrom);

    void changeVacancyExpTo(int id, int expTo);

    void changeVacancyActive(int id, Boolean isActive);
}
