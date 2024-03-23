package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.ContactsInfoService;
import com.example.jobsearch.service.EducationInfoService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final UserService userService;
    private final CategoryService categoryService;
    private final EducationInfoService educationInfoService;
    private final WorkExperienceInfoService workExperienceInfoService;
    private final ContactsInfoService contactsInfoService;

    @Override
    public List<ResumeDto> getResumes() {
        List<Resume> resumes = resumeDao.getResumes();
        return getResumeDtos(resumes);
    }

    @Override
    public ResumeDto getResumeById(int id) throws UserNotFoundException {
        Resume resume = resumeDao.getResumeById(id).orElseThrow(() -> new ResumeNotFoundException("Can not find resume with id: " + id));
        return ResumeDto.builder()
                .id(resume.getId())
                .userEmail(userService.getUserById(resume.getUserId()).getEmail())
                .name(resume.getName())
                .category(categoryService.getCategoryById(resume.getCategoryId()))
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .build();
    }

    @Override
    public List<ResumeDto> getResumesByCategory(String category) {
        List<Resume> resumes = resumeDao.getResumesByCategory(category);
        if (!resumes.isEmpty()) {
            return getResumeDtos(resumes);
        }
        throw new ResumeNotFoundException("Can not find resume with category: " + category);
    }

    @Override
    public List<ResumeDto> getResumesByUserEmail(String email) {
        List<Resume> resumes = resumeDao.getResumesByUserEmail(email);
        if (!resumes.isEmpty()) {
            return getResumeDtos(resumes);
        }
        throw new ResumeNotFoundException("Can not find resume with email: " + email);
    }


    // Служебный метод
    private List<ResumeDto> getResumeDtos(List<Resume> resumes) {
        List<ResumeDto> dtos = new ArrayList<>();
        resumes.forEach(e -> dtos.add(ResumeDto.builder()
                .id(e.getId())
                .userEmail(userService.getUserById(e.getUserId()).getEmail())
                .name(e.getName())
                .category(categoryService.getCategoryById(e.getCategoryId()))
                .salary(e.getSalary())
                .isActive(e.getIsActive())
                .createdDate(e.getCreatedDate())
                .updateTime(e.getUpdateTime())
                .build()));
        return dtos;
    }

    // Служебный метод
    @Override
    public Boolean isResumeInSystem(int id) {
        return resumeDao.isResumeInSystem(id);
    }

    @Override
    public HttpStatus createResume(int userId, ResumeDto resume) {
        if (userService.isEmployee(userId)) {
            if (userService.isEmployee(resume.getUserEmail())) {
                Resume newResume = Resume.builder()
                        .userId(userService.getUserByEmail(resume.getUserEmail()).getId())
                        .name(resume.getName())
                        .categoryId(categoryService.checkInCategories(resume.getCategory().getId()))
                        .salary(resume.getSalary())
                        .isActive(resume.getIsActive())
                        .createdDate(LocalDateTime.now())
                        .build();
                Integer newResumeKey = resumeDao.createResume(newResume);

                educationInfoService.createEducationInfo(resume.getEducationInfos(), newResumeKey);
                workExperienceInfoService.createWorkExperienceInfo(resume.getWorkExperienceInfos(), newResumeKey);
                contactsInfoService.createContactInfo(resume.getContacts(), newResumeKey);

                return HttpStatus.OK;
            }
            throw new ResumeNotFoundException("Не найдено совпдаение Юзера " + userId + " с юзером указанным в резюме");
        }
        throw new ResumeNotFoundException("Юзер " + userId + " не найден среди соискателей");
    }

    @Override
    public HttpStatus changeResume(int userId, ResumeDto resume) {
        if (isResumeInSystem(resume.getId())) {
            if (userService.isEmployee(userId)) {
                if (userId == userService.getUserByEmail(resume.getUserEmail()).getId()) {
                    Resume newResume = Resume.builder()
                            .id(resume.getId())
                            .userId(userService.getUserByEmail(resume.getUserEmail()).getId())
                            .name(resume.getName())
                            .categoryId(categoryService.checkInCategories(resume.getCategory().getId()))
                            .salary(resume.getSalary())
                            .isActive(resume.getIsActive())
                            .updateTime(LocalDateTime.now())
                            .build();

                    resumeDao.changeResume(newResume);
                    educationInfoService.changeEducationInfo(resume.getEducationInfos(), resume.getId());
                    workExperienceInfoService.changeWorkExperienceInfo(resume.getWorkExperienceInfos(), resume.getId());
                    contactsInfoService.changeContactInfo(resume.getContacts(), resume.getId());

                    return HttpStatus.OK;
                }
                throw new ResumeNotFoundException("Не найдено совпдаение Юзера " + userId + " с юзером указанным в резюме");
            }
            throw new ResumeNotFoundException("Юзер " + userId + " не найден среди соискателей");
        }
        throw new ResumeNotFoundException("Резюме с айди " + resume.getId() + " не найдено в системе");
    }

    @Override
    public HttpStatus deleteResumeById(int userId, int id) {
        if (isResumeInSystem(id)) {
            if (userService.isEmployee(userId)) {
                if (userId == userService.getUserByEmail(getResumeById(id).getUserEmail()).getId()) {
                    resumeDao.deleteResumeById(id);
                    return HttpStatus.OK;
                }
                throw new ResumeNotFoundException("Не найдено совпдаение Юзера " + userId + " с юзером указанным в резюме");
            }
            throw new ResumeNotFoundException("Юзер " + userId + " не найден среди соискателей");
        }
        throw new ResumeNotFoundException("Резюме с айди " + id + " не найдено в системе");
    }

    @Override
    public List<ResumeDto> getActiveResumes(int userId) {
        // TODO реализовать выборку активных резюме
        return null;
    }

}
