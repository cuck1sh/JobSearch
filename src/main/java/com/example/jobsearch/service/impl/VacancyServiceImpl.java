package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;

    @Override
    public VacancyDto getVacancyById(int id) throws UserNotFoundException {
        Vacancy vacancy = vacancyDao.getVacancyById(id).orElseThrow(() -> new UserNotFoundException("Can not find vacancy with id: " + id));
        return VacancyDto.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .categoryId(vacancy.getCategoryId())
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .userId(vacancy.getUserId())
                .createdDate(vacancy.getCreatedDate())
                .updateTime(vacancy.getUpdateTime())
                .build();
    }

    @Override
    public List<VacancyDto> getVacancies() {
        List<Vacancy> vacancies = vacancyDao.getVacancies();
        return getVacancyDtos(vacancies);
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(String category) {
        List<Vacancy> vacancies = vacancyDao.getVacanciesByCategory(category);
        return getVacancyDtos(vacancies);
    }

    private List<VacancyDto> getVacancyDtos(List<Vacancy> vacancies) {
        List<VacancyDto> dtos = new ArrayList<>();
        vacancies.forEach(e -> dtos.add(VacancyDto.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .categoryId(e.getCategoryId())
                .salary(e.getSalary())
                .expFrom(e.getExpFrom())
                .expTo(e.getExpTo())
                .isActive(e.getIsActive())
                .userId(e.getUserId())
                .createdDate(e.getCreatedDate())
                .updateTime(e.getUpdateTime())
                .build()));
        return dtos;
    }

    @Override
    public List<Vacancy> getCompanyVacancies(int userId) {
        // TODO реализовать выборку всех вакансий компании
        return null;
    }

    @Override
    public List<Vacancy> getActiveVacancies(int userId) {
        // TODO реализовать выборку активный вакансий компании
        return null;
    }

    @Override
    public void createVacancy(VacancyDto vacancy) {
        // TODO реализовать создание вокансии
    }

    @Override
    public void changeVacancyName(int id, String name) {
        // TODO реализовать смену имени вакансии
    }

    @Override
    public void changeVacancyDescription(int id, String description) {
        // TODO реализовать смену описания
    }

    @Override
    public void changeVacancyCategory(int id, String category) {
        // TODO реализовать смену категории
    }

    @Override
    public void changeVacancySalary(int id, Double salary) {
        // TODO реализовать смену зарплаты
    }

    @Override
    public void changeVacancyExpFrom(int id, int expFrom) {
        // TODO реализовать смену минимального опыта
    }

    @Override
    public void changeVacancyExpTo(int id, int expTo) {
        // TODO реализовать смену максимального опыта
    }

    @Override
    public void changeVacancyActive(int id, Boolean isActive) {
        // TODO реализовать смену статуса активности
    }
}
