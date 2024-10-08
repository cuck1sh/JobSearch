package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.CategoryDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.dto.user.UserMainItem;
import com.example.jobsearch.dto.vacancy.InputVacancyDto;
import com.example.jobsearch.dto.vacancy.VacancyDto;
import com.example.jobsearch.exception.FailedCreation;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.exception.VacancyNotFoundException;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.Vacancy;
import com.example.jobsearch.repository.VacancyRepository;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.VacancyService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
    private final ResumeService resumeService;

    @Override
    public VacancyDto getVacancyById(int id) throws UserNotFoundException {
        if (isVacancyInSystem(id)) {
            Vacancy vacancy = vacancyRepository.findById(id)
                    .orElseThrow(() -> new VacancyNotFoundException("Не найдена вакансия с айди: " + id));

            CategoryDto category = (vacancy.getCategory() != null) ? categoryService.getCategoryById(vacancy.getCategory().getId()) : null;

            return VacancyDto.builder()
                    .id(vacancy.getId())
                    .name(vacancy.getName())
                    .description(vacancy.getDescription())
                    .category(category)
                    .salary(vacancy.getSalary())
                    .expFrom(vacancy.getExpFrom())
                    .expTo(vacancy.getExpTo())
                    .isActive(vacancy.getIsActive())
                    .userEmail(vacancy.getUser().getEmail())
                    .createdDate(vacancy.getCreatedDate())
                    .updateTime(vacancy.getUpdateTime())
                    .companyName(vacancy.getUser().getName())
                    .build();
        } else {
            log.error("Не найдена вакансия с айди: " + id + " для метода getVacancyById(id)");
            return null;
        }
    }

    @Override
    public InputVacancyDto getInputVacancyById(int id) throws UserNotFoundException {
        if (isVacancyInSystem(id)) {
            Vacancy vacancy = vacancyRepository.findById(id)
                    .orElseThrow(() -> new VacancyNotFoundException("Не найдена вакансия с айди: " + id));

            String categoryName = (vacancy.getCategory() != null) ? vacancy.getCategory().getName() : "Пусто";

            return InputVacancyDto.builder()
                    .id(vacancy.getId())
                    .name(vacancy.getName())
                    .category(categoryName)
                    .salary(vacancy.getSalary())
                    .description(vacancy.getDescription())
                    .expFrom(vacancy.getExpFrom())
                    .expTo(vacancy.getExpTo())
                    .isActive(vacancy.getIsActive())
                    .userEmail(vacancy.getUser().getEmail())
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
    public Page<VacancyDto> getVacanciesWithPaging(Pageable pageable, String category) {
        Page<Vacancy> vacancies;

        if (category.equals("none")) {
            vacancies = vacancyRepository.findAllByIsActiveTrue(pageable);

            List<VacancyDto> vacancyDtos = getVacancyDtos(vacancies.getContent());
            return new PageImpl<>(vacancyDtos, pageable, vacancyRepository.countAllByIsActiveTrue());
        } else {
            Integer categoryId = categoryService.checkInCategories(category);
            vacancies = vacancyRepository.findAllByIsActiveTrueAndCategory_Id(categoryId, pageable);

            List<VacancyDto> vacancyDtos = getVacancyDtos(vacancies.getContent());
            return new PageImpl<>(vacancyDtos, pageable, vacancyRepository.countAllByIsActiveTrueAndCategory_Id(categoryId));
        }
    }


    @Override
    public Page<UserMainItem> getVacancyMainItem(Integer userId, Pageable pageable) {
        Page<Vacancy> pagedVacancies = vacancyRepository.findAllByUserId(userId, pageable);
        List<UserMainItem> vacancyDtos = new ArrayList<>();
        pagedVacancies.getContent().forEach(e -> vacancyDtos.add(UserMainItem.builder()
                .id(e.getId())
                .name(e.getName())
                .timestamp(e.getUpdateTime())
                .build()));

        return new PageImpl<>(vacancyDtos, pageable, vacancyRepository.countAllByUserId(userId));
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
                .responseQty(vacancyRepository.countAllByIdAndRespondedApplicants_VacancyId(e.getId(), e.getId()))
                .companyName(e.getUser().getName())
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
        if (!vacancyDto.getName().isBlank() && !vacancyDto.getName().isEmpty()) {
            UserDto user = authenticatedUserProvider.getAuthUser();

            if (!userService.isEmployee(user.getEmail())) {
                LocalDateTime now = LocalDateTime.now();

                Vacancy vacancy = Vacancy.builder()
                        .name(vacancyDto.getName())
                        .user(User.builder().id(user.getId()).build())
                        .createdDate(now)
                        .updateTime(now)
                        .build();

                Integer categoryId = categoryService.checkInCategories(vacancyDto.getCategory());

                if (categoryId != null) {
                    vacancy.setCategory(Category.builder().id(categoryId).build());
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

                vacancy.setIsActive(vacancyDto.getIsActive());
                vacancyRepository.save(vacancy);
            } else {
                log.info("Юзер " + user.getEmail() + " не найден среди работодателей для добавления вакансии");
                throw new UserNotFoundException("Юзер " + user.getEmail() + " не найден среди работодателей для добавления вакансии");
            }
        } else {
            log.error("Попытка создания пустой вакансии");
            throw new FailedCreation("Попытка создания пустой вакансии");
        }
    }


    @Override
    public void changeVacancy(InputVacancyDto vacancyDto) {
        UserDto user = authenticatedUserProvider.getAuthUser();

        if (vacancyDto.getId() != null) {
            if (isVacancyInSystem(vacancyDto.getId())) {
                if (!userService.isEmployee(user.getId())) {
                    if (user.getEmail().equals(vacancyDto.getUserEmail())) {
                        Vacancy vacancy = Vacancy.builder()
                                .id(vacancyDto.getId())
                                .name(vacancyDto.getName())
                                .description(vacancyDto.getDescription())
                                .salary(vacancyDto.getSalary())
                                .expFrom(vacancyDto.getExpFrom())
                                .expTo(vacancyDto.getExpTo())
                                .updateTime(LocalDateTime.now())
                                .build();

                        Integer categoryId = categoryService.checkInCategories(vacancyDto.getCategory());

                        if (categoryId != null) {
                            vacancy.setCategory(Category.builder().id(categoryId).build());
                        } else {
                            vacancy.setCategory(null);
                        }

                        if (vacancyDto.getIsActive() != null) {
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
                        log.error("Попытка изменения чужой вакансии");
                        throw new VacancyNotFoundException("Попытка изменения чужой вакансии");
                    }
                } else {
                    log.error("Юзер " + user.getEmail() + " не найден среди работодателей");
                    throw new VacancyNotFoundException("Юзер " + user.getEmail() + " не найден среди работодателей");
                }
            } else {
                log.error("Вакансия с айди " + vacancyDto.getId() + " не найдена в системе для редактирования");
                throw new VacancyNotFoundException("Вакансия с айди " + vacancyDto.getId() + " не найдена в системе для редактирования");
            }
        } else {
            log.error("Айди изменяемой вакансии не указан");
            throw new VacancyNotFoundException("Айди изменяемой вакансии не указан");
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
            List<Vacancy> vacancies = vacancyRepository.findAllByIsActiveTrueAndUserId(userId);
            return getVacancyDtos(vacancies);
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
    public List<VacancyDto> searchVacancies(String text) {
        return getVacancyDtos(vacancyRepository.search(text.toLowerCase().strip())).reversed();
    }
}
