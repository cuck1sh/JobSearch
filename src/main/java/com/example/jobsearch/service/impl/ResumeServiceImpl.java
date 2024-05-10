package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.resume.InputResumeDto;
import com.example.jobsearch.dto.resume.ResumeDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.dto.user.UserMainItem;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.ResumeRepository;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.ContactsInfoService;
import com.example.jobsearch.service.EducationInfoService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.WorkExperienceInfoService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository repository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final EducationInfoService educationInfoService;
    private final WorkExperienceInfoService workExperienceInfoService;
    private final ContactsInfoService contactsInfoService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public List<ResumeDto> getResumes() {
        List<Resume> resumes = repository.findAll();
        return getResumeDtos(resumes);
    }

    @Override
    public List<ResumeDto> getActiveResumes() {
        List<Resume> resumes = repository.findAllByIsActiveTrue();
        return getResumeDtos(resumes);
    }

    @Override
    public ResumeDto getResumeById(int id) throws UserNotFoundException {
        Resume resume = repository.findById(id).orElseThrow(() -> new ResumeNotFoundException("Can not find resume with id: " + id));
        Integer categoryId = resume.getCategory() != null ? resume.getCategory().getId() : 0;

        return ResumeDto.builder()
                .id(resume.getId())
                .userEmail(resume.getUser().getEmail())
                .name(resume.getName())
                .category(categoryService.getCategoryById(categoryId))
                .salary(resume.getSalary())
                .contacts(contactsInfoService.getContactInfoByResumeId(resume.getId()))
                .workExperienceInfoDtos(workExperienceInfoService.WorkExperienceInfoById(resume.getId()))
                .educationInfos(educationInfoService.getEducationInfoById(resume.getId()))
                .isActive(resume.getIsActive())
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .build();
    }

    @Override
    public InputResumeDto getInputResumeById(int id) throws UserNotFoundException {
        Resume resume = repository.findById(id).orElseThrow(() -> new ResumeNotFoundException("Can not find resume with id: " + id));

        return InputResumeDto.builder()
                .id(resume.getId())
                .userEmail(resume.getUser().getEmail())
                .name(resume.getName())
                .category(resume.getCategory().getName())
                .salary(resume.getSalary())
                .workExperienceInfoDtos(workExperienceInfoService.WorkExperienceInfoById(resume.getId()))
                .educationInfos(educationInfoService.getEducationInfoById(resume.getId()))
                .contacts(contactsInfoService.getContactInfoByResumeId(resume.getId()))
                .isActive(resume.getIsActive())
                .build();
    }

    @Override
    public List<ResumeDto> getResumesByCategory(String category) {
        List<Resume> resumes = repository.findAllByCategoryNameAndIsActiveTrue(category);
        if (!resumes.isEmpty()) {
            return getResumeDtos(resumes);
        }
        throw new ResumeNotFoundException("Can not find resume with category: " + category);
    }

    @Override
    public List<ResumeDto> getResumesByUserId(int id) {
        List<Resume> resumes = repository.findAllByUserIdAndIsActiveTrue(id);
        if (!resumes.isEmpty()) {
            return getResumeDtos(resumes);
        }
        throw new ResumeNotFoundException("Can not find resume with user id: " + id);
    }

    @Override
    public Page<UserMainItem> getResumeMainItem(Integer userId, Pageable pageable) {
        Page<Resume> pagedResumes = repository.findAllByUserId(userId, pageable);
        List<UserMainItem> resumeDtos = new ArrayList<>();
        pagedResumes.getContent().forEach(e -> resumeDtos.add(UserMainItem.builder()
                .id(e.getId())
                .name(e.getName())
                .timestamp(e.getUpdateTime())
                .build()));


        return new PageImpl<>(resumeDtos, pageable, repository.countByUserId(userId));
    }

    @Override
    public Integer getResumesCount() {
        return Math.toIntExact(repository.countAllByIsActiveTrue());
    }

    @Override
    public Page<ResumeDto> getResumesWithPaging(Pageable pageable, String filter) {
        Page<Resume> resumes;

        if (filter.equals("none")) {
            resumes = repository.findAllByIsActiveTrue(pageable);

            List<ResumeDto> resumeDtos = getResumeDtos(resumes.getContent());
            return new PageImpl<>(resumeDtos, pageable, repository.countAllByIsActiveTrue());
        } else {
            Integer categoryId = categoryService.checkInCategories(filter);
            resumes = repository.findAllByIsActiveTrueAndCategory_Id(categoryId, pageable);

            List<ResumeDto> resumeDtos = getResumeDtos(resumes.getContent());
            return new PageImpl<>(resumeDtos, pageable, repository.countAllByIsActiveTrueAndCategory_Id(categoryId));
        }
    }


    // Служебный метод
    private List<ResumeDto> getResumeDtos(List<Resume> resumes) {
        List<ResumeDto> dtos = new ArrayList<>();
        resumes.forEach(e -> dtos.add(ResumeDto.builder()
                .id(e.getId())
                .userEmail(e.getUser().getEmail())
                .name(e.getName())
                .category(categoryService.getCategoryById(e.getCategory().getId()))
                .salary(e.getSalary())
                .contacts(contactsInfoService.getContactInfoByResumeId(e.getId()))
                .workExperienceInfoDtos(workExperienceInfoService.WorkExperienceInfoById(e.getId()))
                .educationInfos(educationInfoService.getEducationInfoById(e.getId()))
                .isActive(e.getIsActive())
                .createdDate(e.getCreatedDate())
                .updateTime(e.getUpdateTime())
                .build()));

        return dtos;
    }

    // Служебный метод
    @Override
    public Boolean isResumeInSystem(int id) {
        return repository.existsById(id);
    }

    @Override
    public Boolean isUsersResumesInSystem(int userId) {
        return repository.existsByUserId(userId);
    }

    @Override
    public void createResume(InputResumeDto resumeDto) {
        UserDto user = authenticatedUserProvider.getAuthUser();

        if (userService.isEmployee(user.getEmail())) {
            Resume newResume = Resume.builder()
                    .user(User.builder().id(user.getId()).build())
                    .name(resumeDto.getName())
                    .salary(resumeDto.getSalary())
                    .isActive(resumeDto.getIsActive())
                    .createdDate(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();

            Integer categoryId = categoryService.checkInCategories(resumeDto.getCategory());

            if (categoryId != null) {
                newResume.setCategory(Category.builder().id(categoryId).build());
            }

            Integer newResumeKey = repository.save(newResume).getId();

            if (resumeDto.getWorkExperienceInfoDtos() != null) {
                workExperienceInfoService.createWorkExperienceInfo(resumeDto.getWorkExperienceInfoDtos(), newResumeKey);
            }

            if (resumeDto.getEducationInfos() != null) {
                educationInfoService.createEducationInfo(resumeDto.getEducationInfos(), newResumeKey);
            }
            if (resumeDto.getContacts() != null) {
                contactsInfoService.createContactInfo(resumeDto.getContacts(), newResumeKey);
            }
        } else {
            log.error("Юзер " + user.getEmail() + " не найден среди соискателей");
        }
    }

    @Override
    public void changeResume(InputResumeDto resume) {
        UserDto user = authenticatedUserProvider.getAuthUser();

        if (user.getEmail().equals(resume.getUserEmail())) {
            if (isResumeInSystem(resume.getId())) {

                Resume newResume = Resume.builder()
                        .id(resume.getId())
                        .user(User.builder().id(user.getId()).build())
                        .name(resume.getName())
                        .category(Category.builder().id(categoryService.checkInCategories(resume.getCategory())).build())
                        .updateTime(LocalDateTime.now())
                        .build();

                if (resume.getSalary() != null) {
                    newResume.setSalary(resume.getSalary());
                }

                newResume.setIsActive(resume.getIsActive() != null);
                repository.updateBy(newResume.getName(),
                        newResume.getCategory().getId(),
                        newResume.getSalary(),
                        newResume.getIsActive(),
                        newResume.getUpdateTime(),
                        newResume.getId());

                log.error("WORKEXPDTOS: {}", resume.getWorkExperienceInfoDtos());

                if (resume.getWorkExperienceInfoDtos() != null) {
                    resume.getWorkExperienceInfoDtos().forEach(e -> {
                        log.error("WORKEXP ID: {}", e.getId());
                        if (e.getId() == null) {
                            workExperienceInfoService.createWorkExperienceInfo(e, resume.getId());
                        } else {
                            workExperienceInfoService.changeWorkExperienceInfo(e);
                        }
                    });

                }

                if (resume.getEducationInfos() != null) {
                    resume.getEducationInfos().forEach(e -> {
                        if (e.getId() == null) {
                            educationInfoService.createEducationInfo(e, resume.getId());
                        } else {
                            educationInfoService.changeEducationInfo(e);
                        }
                    });
                }

                if (resume.getContacts() != null) {
                    contactsInfoService.createContactInfo(resume.getContacts(), resume.getId());
                }

                contactsInfoService.updateContactInfo(resume.getContacts(), newResume.getId());
            } else {
                log.error("Резюме с айди " + resume.getId() + " не найдено в системе для его редактирования");
                throw new ResumeNotFoundException("Резюме с айди " + resume.getId() + " не найдено в системе для его редактирования");
            }
        } else {
            log.error("Попытка юзера " + user.getEmail() + " исправления чужого резюме с айди: " + resume.getId());
            throw new ResumeNotFoundException("Попытка юзера " + user.getEmail() + " исправления чужого резюме с айди: " + resume.getId());
        }
    }

    @Override
    public HttpStatus deleteResumeById(Authentication auth, int id) {
        UserDto user = authenticatedUserProvider.getAuthUser();
        if (isResumeInSystem(id)) {
            if (userService.isEmployee(user.getEmail())) {
                if (user.getEmail().equals(userService.getUserByEmail(getResumeById(id).getUserEmail()).getEmail())) {
                    repository.deleteById(id);
                    return HttpStatus.OK;
                }
                throw new ResumeNotFoundException("Не найдено совпдаение Юзера " + user.getEmail() + " с юзером указанным в резюме");
            }
            throw new ResumeNotFoundException("Юзер " + user.getEmail() + " не найден среди соискателей");
        }
        throw new ResumeNotFoundException("Резюме с айди " + id + " не найдено в системе для его удаления");
    }

    @Override
    public UserDto getUserByResume(int resumeId) {
        Resume resume = repository.findById(resumeId)
                .orElseThrow(() -> new ResumeNotFoundException("Резюме не найдено"));
        return userService.getUserById(resume.getUser().getId());
    }

    @Override
    public void getResume(int id, Model model) {
        UserDto authUser = authenticatedUserProvider.getAuthUser();
        ResumeDto resumeDto = getResumeById(id);

        if (authUser.getEmail().equals(resumeDto.getUserEmail())) {
            model.addAttribute("resume", resumeDto);
        } else {
            throw new ResumeNotFoundException("Несоответствие юзера и юзера в резюме");
        }


    }
}
