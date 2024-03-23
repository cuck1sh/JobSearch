package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.exception.VacancyNotFoundException;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final UserService userService;

    @Override
    public VacancyDto getVacancyById(int id) throws UserNotFoundException {
        Vacancy vacancy = vacancyDao.getVacancyById(id).orElseThrow(() -> new VacancyNotFoundException("Can not find vacancy with id: " + id));
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
        if (!vacancies.isEmpty()) {
            return getVacancyDtos(vacancies);
        }
        throw new VacancyNotFoundException("Can not find vacancy with category: " + category);
    }

    // Служебный метод
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

    // Служебный метод
    @Override
    public Boolean isVacancyInSystem(int id) {
        return vacancyDao.isVacancyInSystem(id);
    }

    // Служебный метод
    @Override
    public Boolean isVacancyActive(int vacancyId) {
        return vacancyDao.isVacancyActive(vacancyId);
    }

    @Override
    public HttpStatus createVacancy(int userId, VacancyDto vacancyDto) {
        if (!userService.isEmployee(userId)) {
            if (userId == vacancyDto.getUserId()) {
                Vacancy vacancy = Vacancy.builder()
                        .name(vacancyDto.getName())
                        .description(vacancyDto.getDescription())
                        .categoryId(vacancyDto.getCategoryId())
                        .salary(vacancyDto.getSalary())
                        .expFrom(vacancyDto.getExpFrom())
                        .expTo(vacancyDto.getExpTo())
                        .isActive(vacancyDto.getIsActive())
                        .userId(userId)
                        .createdDate(LocalDateTime.now())
                        .build();

                vacancyDao.createVacancy(vacancy);
                return HttpStatus.OK;
            }
            throw new VacancyNotFoundException("Не найдено совпдаение Юзера " + userId + " с юзером указанным в вакансии");
        }
        throw new VacancyNotFoundException("Юзер " + userId + " не найден среди работодателей");
    }

    @SneakyThrows
    @Override
    public HttpStatus changeVacancy(int userId, VacancyDto vacancyDto) {
        if (isVacancyInSystem(vacancyDto.getId())) {
            if (!userService.isEmployee(userId)) {
                if (userId == vacancyDto.getUserId()) {
                    Vacancy vacancy = Vacancy.builder()
                            .id(vacancyDto.getId())
                            .name(vacancyDto.getName())
                            .description(vacancyDto.getDescription())
                            .categoryId(vacancyDto.getCategoryId())
                            .salary(vacancyDto.getSalary())
                            .expFrom(vacancyDto.getExpFrom())
                            .expTo(vacancyDto.getExpTo())
                            .isActive(vacancyDto.getIsActive())
                            .updateTime(LocalDateTime.now())
                            .build();

                    vacancyDao.changeVacancy(vacancy);
                    return HttpStatus.OK;
                }
                throw new VacancyNotFoundException("Не найдено совпдаение Юзера " + userId + " с юзером указанным в вакансии");
            }
            throw new VacancyNotFoundException("Юзер " + userId + " не найден среди работодателей");
        }
        throw new ResumeNotFoundException("Вакансия с айди " + vacancyDto.getId() + " не найдена в системе");
    }

    @SneakyThrows
    @Override
    public HttpStatus delete(int userId, int id) {
        if (isVacancyInSystem(id)) {
            if (!userService.isEmployee(userId)) {
                if (userId == getVacancyById(id).getUserId()) {
                    vacancyDao.delete(id);
                    return HttpStatus.OK;
                }
                throw new VacancyNotFoundException("Не найдено совпдаение Юзера " + userId + " с юзером указанным в вакансии");
            }
            throw new VacancyNotFoundException("Юзер " + userId + " не найден среди работодателей");
        }
        throw new ResumeNotFoundException("Вакансия с айди " + id + " не найдена в системе");
    }

    @Override
    public List<VacancyDto> getAllVacanciesByCompany(int userId) {
        if (userService.isUserInSystem(userId)) {
            List<Vacancy> vacancies = vacancyDao.getAllVacancyByCompany(userId);
            if (!vacancies.isEmpty()) {
                return getVacancyDtos(vacancies);
            }
            throw new VacancyNotFoundException("У Юзера " + userId + " не найдено вакансий");
        }
        throw new VacancyNotFoundException("Юзер " + userId + " не найден");
    }

    @Override
    public List<VacancyDto> getVacanciesByCategoryAndUser(int userId, String category) {
        if (userService.isUserInSystem(userId)) {
            List<Vacancy> vacancies = vacancyDao.getVacanciesByCategoryAndUser(userId, category);
            if (!vacancies.isEmpty()) {
                return getVacancyDtos(vacancies);
            }
            throw new VacancyNotFoundException("У Юзера " + userId + " не найдено вакансий");
        }
        throw new VacancyNotFoundException("Юзер " + userId + " не найден");
    }

    @Override
    public List<Vacancy> getActiveVacancies(int userId) {
        // TODO реализовать выборку активных вакансий компании
        return null;
    }
}
