package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.resume.InputContactInfoDto;
import com.example.jobsearch.dto.resume.InputResumeDto;
import com.example.jobsearch.dto.resume.ResumeDto;
import com.example.jobsearch.dto.user.UserDto;
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
        return ResumeDto.builder()
                .id(resume.getId())
                .userEmail(resume.getUser().getEmail())
                .name(resume.getName())
                .category(categoryService.getCategoryById(resume.getCategory().getId()))
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
    public Integer getResumesCount() {
        return Math.toIntExact(repository.countAllByIsActiveTrue());
    }

    @Override
    public List<ResumeDto> getResumesWithPaging(Integer page, Integer pageSize) {
        int count = getResumesCount();
        int totalPages = count / pageSize;

        if (totalPages <= page) {
            page = totalPages;
        } else if (page < 0) {
            page = 0;
        }

        int offset = page * pageSize;

        List<Resume> resumes = repository.findPagedResumes(pageSize, offset);

        return getResumeDtos(resumes);
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
                    .category(Category.builder().id(categoryService.checkInCategories(resumeDto.getCategory())).build())
                    .salary(resumeDto.getSalary())
                    .isActive(resumeDto.getIsActive())
                    .createdDate(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();


            Integer newResumeKey = repository.saveAndFlush(newResume).getId();

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
    public void changeResume(InputResumeDto resume, InputContactInfoDto contacts) {
        String userEmail = authenticatedUserProvider.getAuthUser().getEmail();

        if (userEmail.equals(resume.getUserEmail())) {
            if (isResumeInSystem(resume.getId())) {
                Integer userId = userService.getUserByEmail(userEmail).getId();

                Resume newResume = Resume.builder()
                        .id(resume.getId())
                        .user(User.builder().id(userId).build())
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

                contactsInfoService.updateContactInfo(contacts, newResume.getId());
            } else {
                log.error("Резюме с айди " + resume.getId() + " не найдено в системе для его редактирования");
            }
        } else {
            log.error("Попытка юзера " + userEmail + " исправления чужого резюме с айди: " + resume.getId());
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
