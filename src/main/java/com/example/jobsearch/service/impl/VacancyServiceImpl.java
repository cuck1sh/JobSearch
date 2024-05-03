package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.dto.vacancy.InputVacancyDto;
import com.example.jobsearch.dto.vacancy.VacancyDto;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.exception.VacancyNotFoundException;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.repository.VacancyRepository;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public VacancyDto getVacancyById(int id) throws UserNotFoundException {
        if (isVacancyInSystem(id)) {
            Vacancy vacancy = vacancyRepository.findById(id)
                    .orElseThrow(() -> new VacancyNotFoundException("Не найдена вакансия с айди: " + id));
            return VacancyDto.builder()
                    .id(vacancy.getId())
                    .name(vacancy.getName())
                    .description(vacancy.getDescription())
                    .category(categoryService.getCategoryById(vacancy.getCategory().getId()))
                    .salary(vacancy.getSalary())
                    .expFrom(vacancy.getExpFrom())
                    .expTo(vacancy.getExpTo())
                    .isActive(vacancy.getIsActive())
                    .userEmail(vacancy.getUser().getEmail())
                    .createdDate(vacancy.getCreatedDate())
                    .updateTime(vacancy.getUpdateTime())
                    .build();
        } else {
            log.error("Не найдена вакансия с айди: " + id + " для метода getVacancyById(id)");
            return null;
        }
    }

    @Override
    public UserDto getUserByVacancy(int vacancyId) {
        VacancyDto vacancyDto = getVacancyById(vacancyId);
        return userService.getUserByEmail(vacancyDto.getUserEmail());
    }

    @Override
    public List<VacancyDto> getVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        return getVacancyDtos(vacancies);
    }

    @Override
    public List<VacancyDto> getActiveVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAllByIsActiveTrue();
        return getVacancyDtos(vacancies);
    }

    @Override
    public Integer getVacanciesCount() {
        return Math.toIntExact(vacancyRepository.countAllByIsActiveTrue());
    }

    @Override
    public Integer getVacanciesWithCategoryCount(int categoryId) {
        return vacancyRepository.countAllByIsActiveTrueAndCategoryId(categoryId);
    }

    @Override
    public List<VacancyDto> getVacanciesWithPaging(Integer page, Integer pageSize, String category) {
        int count;
        int totalPages;
        int offset;
        List<Vacancy> vacancies;

        if (category.equals("none")) {
            count = getVacanciesCount();
            totalPages = count / pageSize;

            if (totalPages <= page) {
                page = totalPages;
            } else if (page < 0) {
                page = 0;
            }

            offset = page * pageSize;

            vacancies = vacancyRepository.findPagedVacancies(pageSize, offset);
        } else {
            int categoryId = categoryService.checkInCategories(category);

            count = getVacanciesWithCategoryCount(categoryId);
            totalPages = count / pageSize;

            if (totalPages <= page) {
                page = totalPages;
            } else if (page < 0) {
                page = 0;
            }

            offset = page * pageSize;

            vacancies = vacancyRepository.findPagedVacanciesWithCategory(pageSize, offset, categoryId);
        }

        return getVacancyDtos(vacancies);
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(String category) {
        List<Vacancy> vacancies = vacancyRepository.findAllByIsActiveTrueAndCategoryName(category);
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
                .category(categoryService.getCategoryById(e.getCategory().getId()))
                .salary(e.getSalary())
                .expFrom(e.getExpFrom())
                .expTo(e.getExpTo())
                .isActive(e.getIsActive())
                .userEmail(e.getUser().getEmail())
                .createdDate(e.getCreatedDate())
                .updateTime(e.getUpdateTime())
                .build()));
        return dtos;
    }

    // Служебный метод
    @Override
    public Boolean isVacancyInSystem(int id) {
        return vacancyRepository.existsById(id);
    }

    @Override
    public Boolean isUsersVacanciesInSystem(int userId) {
        return vacancyRepository.existsByUserId(userId);
    }

    // Служебный метод
    @Override
    public Boolean isVacancyActive(int vacancyId) {
        return vacancyRepository.existsByIsActiveTrueAndId(vacancyId);
    }

    @Override
    public void createVacancy(InputVacancyDto vacancyDto) {
        UserDto user = authenticatedUserProvider.getAuthUser();

        if (!userService.isEmployee(user.getEmail())) {
            LocalDateTime now = LocalDateTime.now();

            Vacancy vacancy = Vacancy.builder()
                    .name(vacancyDto.getName())
                    .user(User.builder().id(user.getId()).build())
                    .createdDate(now)
                    .updateTime(now)
                    .build();

            if (!vacancyDto.getCategory().equals("Выберите категорию")) {
                vacancy.setCategory(Category.builder().id(Integer.parseInt(vacancyDto.getCategory())).build());
            }

            if (vacancyDto.getSalary() != null) {
                vacancy.setSalary(vacancyDto.getSalary());
            }

            if (!vacancyDto.getDescription().isBlank()) {
                vacancy.setDescription(vacancyDto.getDescription());
            }

            if (vacancyDto.getExpFrom() != null) {
                vacancy.setExpFrom(vacancyDto.getExpFrom());
            }

            if (vacancyDto.getExpTo() != null) {
                vacancy.setExpTo(vacancyDto.getExpTo());
            }

            vacancy.setIsActive(vacancyDto.getIsActive() != null);
            vacancyRepository.save(vacancy);
        } else {
            log.info("Юзер " + user.getEmail() + " не найден среди работодателей для добавления вакансии");
        }
    }

    @Override
    public void changeVacancy(InputVacancyDto vacancyDto) {
        String userEmail = authenticatedUserProvider.getAuthUser().getEmail();

        if (vacancyDto.getId() != null) {
            if (isVacancyInSystem(vacancyDto.getId())) {
                if (!userService.isEmployee(userEmail)) {
                    Vacancy vacancy = Vacancy.builder()
                            .id(vacancyDto.getId())
                            .name(vacancyDto.getName())
                            .description(vacancyDto.getDescription())
                            .salary(vacancyDto.getSalary())
                            .expFrom(vacancyDto.getExpFrom())
                            .expTo(vacancyDto.getExpTo())
                            .updateTime(LocalDateTime.now())
                            .build();

                    if (!vacancyDto.getCategory().equals("Выберите категорию")) {
                        vacancy.setCategory(Category.builder().id(Integer.parseInt(vacancyDto.getCategory())).build());
                    } else {
                        vacancy.setCategory(null);
                    }

                    if (vacancyDto.getIsActive() == null) {
                        vacancy.setIsActive(false);
                    } else {
                        vacancy.setIsActive(vacancyDto.getIsActive());
                    }

                    vacancyRepository.updateBy(vacancy.getName(),
                            vacancy.getDescription(),
                            vacancy.getCategory().getId(),
                            vacancy.getSalary(),
                            vacancy.getExpFrom(),
                            vacancy.getExpTo(),
                            vacancy.getIsActive(),
                            vacancy.getUpdateTime(),
                            vacancy.getId());
                } else {
                    log.error("Юзер " + userEmail + " не найден среди работодателей");
                }
            } else {
                log.error("Вакансия с айди " + vacancyDto.getId() + " не найдена в системе для редактирования");
            }
        } else {
            log.error("Айди изменяемой вакансии не указан");
        }
    }

    @Override
    public HttpStatus delete(Authentication auth, int id) {
        UserDto user = authenticatedUserProvider.getAuthUser();
        if (isVacancyInSystem(id)) {
            if (!userService.isEmployee(user.getEmail())) {
                if (user.getEmail().equals(getVacancyById(id).getUserEmail())) {
                    vacancyRepository.deleteById(id);
                    return HttpStatus.OK;
                }
                throw new VacancyNotFoundException("Не найдено совпдаение Юзера " + user.getEmail() + " с юзером указанным в вакансии");
            }
            throw new VacancyNotFoundException("Юзер " + user.getEmail() + " не найден среди работодателей");
        }
        throw new ResumeNotFoundException("Вакансия с айди " + id + " не найдена в системе");
    }

    @Override
    public List<VacancyDto> getAllVacanciesByCompany(int userId) {
        if (userService.isUserInSystem(userId)) {
            List<Vacancy> vacancies = vacancyRepository.findAllByUserId(userId);
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
            List<Vacancy> vacancies = vacancyRepository.findAllByUserIdAndCategoryNameAndIsActiveTrue(userId, category);
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
