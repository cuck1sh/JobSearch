package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dto.InputResumeDto;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
    public List<ResumeDto> getActiveResumes() {
        List<Resume> resumes = resumeDao.getActiveResumes();
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
                .contacts(contactsInfoService.getContactInfoByResumeId(resume.getId()))
                .workExperienceInfos(workExperienceInfoService.WorkExperienceInfoById(resume.getId()))
                .educationInfos(educationInfoService.getEducationInfoById(resume.getId()))
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
    public List<ResumeDto> getResumesByUserId(int id) {
        List<Resume> resumes = resumeDao.getResumesByUserEmail(id);
        if (!resumes.isEmpty()) {
            return getResumeDtos(resumes);
        }
        throw new ResumeNotFoundException("Can not find resume with user id: " + id);
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
                .contacts(contactsInfoService.getContactInfoByResumeId(e.getId()))
                .workExperienceInfos(workExperienceInfoService.WorkExperienceInfoById(e.getId()))
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
        return resumeDao.isResumeInSystem(id);
    }

    @Override
    public HttpStatus createResume(Authentication auth, InputResumeDto resume) {
        User user = (User) auth.getPrincipal();
        if (userService.isEmployee(user.getUsername())) {
            if (userService.isEmployee(resume.getUserEmail())) {
                Resume newResume = Resume.builder()
                        .userId(userService.getUserByEmail(resume.getUserEmail()).getId())
                        .name(resume.getName())
                        .categoryId(categoryService.checkInCategories(resume.getCategory()))
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
            throw new ResumeNotFoundException("Не найдено совпдаение Юзера " + user.getUsername() + " с юзером указанным в резюме");
        }
        throw new ResumeNotFoundException("Юзер " + user.getUsername() + " не найден среди соискателей");
    }

    @Override
    public HttpStatus changeResume(Authentication auth, InputResumeDto resume) {
        User user = (User) auth.getPrincipal();
        if (isResumeInSystem(resume.getId())) {
            if (userService.isEmployee(user.getUsername())) {
                if (user.getUsername().equals(userService.getUserByEmail(resume.getUserEmail()).getEmail())) {
                    Resume newResume = Resume.builder()
                            .id(resume.getId())
                            .userId(userService.getUserByEmail(resume.getUserEmail()).getId())
                            .name(resume.getName())
                            .categoryId(categoryService.checkInCategories(resume.getCategory()))
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
                throw new ResumeNotFoundException("Не найдено совпдаение Юзера " + user.getUsername() + " с юзером указанным в резюме");
            }
            throw new ResumeNotFoundException("Юзер " + user.getUsername() + " не найден среди соискателей");
        }
        throw new ResumeNotFoundException("Резюме с айди " + resume.getId() + " не найдено в системе");
    }

    @Override
    public HttpStatus deleteResumeById(Authentication auth, int id) {
        User user = (User) auth.getPrincipal();
        if (isResumeInSystem(id)) {
            if (userService.isEmployee(user.getUsername())) {
                if (user.getUsername().equals(userService.getUserByEmail(getResumeById(id).getUserEmail()).getEmail())) {
                    resumeDao.deleteResumeById(id);
                    return HttpStatus.OK;
                }
                throw new ResumeNotFoundException("Не найдено совпдаение Юзера " + user.getUsername() + " с юзером указанным в резюме");
            }
            throw new ResumeNotFoundException("Юзер " + user.getUsername() + " не найден среди соискателей");
        }
        throw new ResumeNotFoundException("Резюме с айди " + id + " не найдено в системе");
    }

    @Override
    public void getResume(Authentication auth, int id, Model model) {
        User user = (User) auth.getPrincipal();
        ResumeDto resumeDto = getResumeById(id);

        if (userService.getUserByEmail(user.getUsername()).getEmail().equals(resumeDto.getUserEmail())) {
            model.addAttribute("resume", resumeDto);
        } else {
            throw new ResumeNotFoundException("Несоответствие юзера и юзера в резюме");
        }
    }
}
