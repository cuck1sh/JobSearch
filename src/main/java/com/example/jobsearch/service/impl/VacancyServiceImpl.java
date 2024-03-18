package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final UserService userService;

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

    @SneakyThrows
    private boolean isEmployer(int userId) {
        return "Работодатель".equalsIgnoreCase(userService.getUserById(userId).getAccountType());
    }

    @Override
    public Boolean isVacancyInSystem(int id) {
        return vacancyDao.isVacancyInSystem(id);
    }

    @Override
    public HttpStatus createVacancy(VacancyDto vacancy) {
        if (isEmployer(vacancy.getUserId())) {
            vacancyDao.createVacancy(vacancy);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @SneakyThrows
    @Override
    public HttpStatus changeVacancyName(int id, String name) {
        if (isVacancyInSystem(id)) {
            vacancyDao.changeVacancyName(id, name);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus changeVacancyDescription(int id, String description) {
        if (isVacancyInSystem(id)) {
            vacancyDao.changeVacancyDescription(id, description);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus changeVacancyCategory(int id, String category) {
        if (isVacancyInSystem(id)) {
            vacancyDao.changeVacancyCategory(id, category);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus changeVacancySalary(int id, Double salary) {
        if (isVacancyInSystem(id)) {
            vacancyDao.changeVacancySalary(id, salary);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus changeVacancyExp(int id, int expFrom, int expTo) {
        if (isVacancyInSystem(id)) {
            vacancyDao.changeVacancyExp(id, expFrom, expTo);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus changeVacancyActive(int id, Boolean isActive) {
        if (isVacancyInSystem(id)) {
            vacancyDao.changeVacancyActive(id, isActive);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus delete(int id) {
        if (isVacancyInSystem(id)) {
            vacancyDao.delete(id);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public List<VacancyDto> getAllVacanciesByCompany(int userId) {
        if (userService.isUserInSystem(userId)) {
            List<Vacancy> vacancies = vacancyDao.getAllVacancyByCompany(userId);
            return getVacancyDtos(vacancies);
        }
        return null;
    }

    @Override
    public List<VacancyDto> getVacanciesByCategoryAndUser(int userId, String category) {
        if (userService.isUserInSystem(userId)) {
            List<Vacancy> vacancies = vacancyDao.getVacanciesByCategoryAndUser(userId, category);
            return getVacancyDtos(vacancies);
        }
        return null;
    }

    @Override
    public List<Vacancy> getActiveVacancies(int userId) {
        // TODO реализовать выборку активный вакансий компании
        return null;
    }
}
