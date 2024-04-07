package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.dto.VacancyDto;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.exception.VacancyNotFoundException;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public VacancyDto getVacancyById(int id) throws UserNotFoundException {
        Vacancy vacancy = vacancyDao.getVacancyById(id).orElseThrow(() -> new VacancyNotFoundException("Can not find vacancy with id: " + id));
        return VacancyDto.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .category(categoryService.getCategoryById(vacancy.getCategoryId()))
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .userEmail(userService.getUserById(vacancy.getUserId()).getEmail())
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
    public List<VacancyDto> getActiveVacancies() {
        List<Vacancy> vacancies = vacancyDao.getActiveVacancies();
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
                .category(categoryService.getCategoryById(e.getCategoryId()))
                .salary(e.getSalary())
                .expFrom(e.getExpFrom())
                .expTo(e.getExpTo())
                .isActive(e.getIsActive())
                .userEmail(userService.getUserById(e.getUserId()).getEmail())
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

    @Override
    public Boolean isUsersVacanciesInSystem(int userId) {
        return vacancyDao.isUsersVacanciesInSystem(userId);
    }

    // Служебный метод
    @Override
    public Boolean isVacancyActive(int vacancyId) {
        return vacancyDao.isVacancyActive(vacancyId);
    }

    @Override
    public HttpStatus createVacancy(Authentication auth, VacancyDto vacancyDto) {
        User user = (User) auth.getPrincipal();
        if (!userService.isEmployee(user.getUsername())) {
            if (user.getUsername().equals(vacancyDto.getUserEmail())) {
                Vacancy vacancy = Vacancy.builder()
                        .name(vacancyDto.getName())
                        .description(vacancyDto.getDescription())
                        .categoryId(categoryService.checkInCategories(vacancyDto.getCategory().getId()))
                        .salary(vacancyDto.getSalary())
                        .expFrom(vacancyDto.getExpFrom())
                        .expTo(vacancyDto.getExpTo())
                        .isActive(vacancyDto.getIsActive())
                        .userId(userService.getUserByEmail(user.getUsername()).getId())
                        .createdDate(LocalDateTime.now())
                        .build();

                vacancyDao.createVacancy(vacancy);
                return HttpStatus.OK;
            }
            throw new VacancyNotFoundException("Не найдено совпдаение Юзера " + user.getUsername() + " с юзером указанным в вакансии");
        }
        throw new VacancyNotFoundException("Юзер " + user.getUsername() + " не найден среди работодателей");
    }

    @Override
    public HttpStatus changeVacancy(Authentication auth, VacancyDto vacancyDto) {
        User user = (User) auth.getPrincipal();
        if (vacancyDto.getId() != null) {
            if (isVacancyInSystem(vacancyDto.getId())) {
                if (!userService.isEmployee(user.getUsername())) {
                    if (user.getUsername().equals(vacancyDto.getUserEmail())) {
                        Vacancy vacancy = Vacancy.builder()
                                .id(vacancyDto.getId())
                                .name(vacancyDto.getName())
                                .description(vacancyDto.getDescription())
                                .categoryId(categoryService.checkInCategories(vacancyDto.getCategory().getId()))
                                .salary(vacancyDto.getSalary())
                                .expFrom(vacancyDto.getExpFrom())
                                .expTo(vacancyDto.getExpTo())
                                .isActive(vacancyDto.getIsActive())
                                .updateTime(LocalDateTime.now())
                                .build();

                        vacancyDao.changeVacancy(vacancy);
                        return HttpStatus.OK;
                    }
                    throw new VacancyNotFoundException("Не найдено совпдаение Юзера " + user.getUsername() + " с юзером указанным в вакансии");
                }
                throw new VacancyNotFoundException("Юзер " + user.getUsername() + " не найден среди работодателей");
            }
            throw new VacancyNotFoundException("Вакансия с айди " + vacancyDto.getId() + " не найдена в системе");
        }
        throw new VacancyNotFoundException("Айди изменяемой вакансии не указан");
    }

    @Override
    public HttpStatus delete(Authentication auth, int id) {
        User user = (User) auth.getPrincipal();
        if (isVacancyInSystem(id)) {
            if (!userService.isEmployee(user.getUsername())) {
                if (user.getUsername().equals(getVacancyById(id).getUserEmail())) {
                    vacancyDao.delete(id);
                    return HttpStatus.OK;
                }
                throw new VacancyNotFoundException("Не найдено совпдаение Юзера " + user.getUsername() + " с юзером указанным в вакансии");
            }
            throw new VacancyNotFoundException("Юзер " + user.getUsername() + " не найден среди работодателей");
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
    public void getVacancy(int id, Model model) {
        VacancyDto vacancyDto = getVacancyById(id);

        if (isVacancyInSystem(id)) {
            model.addAttribute("vacancy", vacancyDto);
        } else {
            throw new ResumeNotFoundException("Не найдена вакансия");
        }
    }
}
