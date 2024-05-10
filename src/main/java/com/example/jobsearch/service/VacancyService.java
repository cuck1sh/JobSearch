package com.example.jobsearch.service;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.dto.user.UserMainItem;
import com.example.jobsearch.dto.vacancy.InputVacancyDto;
import com.example.jobsearch.dto.vacancy.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {
    VacancyDto getVacancyById(int id) throws UserNotFoundException;

    InputVacancyDto getInputVacancyById(int id) throws UserNotFoundException;
    List<VacancyDto> getVacancies();
    List<VacancyDto> getActiveVacancies();
    Boolean isVacancyInSystem(int id);
    Boolean isUsersVacanciesInSystem(int userId);
    Boolean isVacancyActive(int vacancyId);
    void createVacancy(InputVacancyDto vacancy);
    void changeVacancy(InputVacancyDto vacancy);
    HttpStatus delete(Authentication auth, int id);
    List<VacancyDto> getAllVacanciesByCompany(int userId);

    Page<UserMainItem> getVacancyMainItem(Integer userId, Pageable pageable);


    List<VacancyDto> getVacanciesByCategory(String category);
    List<VacancyDto> getVacanciesByCategoryAndUser(int userId, String category);

    UserDto getUserByVacancy(int vacancyId);

    Page<VacancyDto> getVacanciesWithPaging(Pageable pageable, String category);

    Integer getVacanciesCount();

    Integer getVacanciesWithCategoryCount(int categoryId);

    List<VacancyDto> searchVacancies(String text);
}
