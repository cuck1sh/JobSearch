package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dto.resume.InputContactInfoDto;
import com.example.jobsearch.dto.resume.InputResumeDto;
import com.example.jobsearch.dto.resume.ResumeDto;
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
    public Boolean isUsersResumesInSystem(int userId) {
        return resumeDao.isUsersResumesInSystem(userId);
    }

    @Override
    public Integer createResume(String userEmail) {
        if (userService.isEmployee(userEmail)) {
            return resumeDao.createResume(userService.getUserByEmail(userEmail).getId());
        }
        throw new ResumeNotFoundException("Юзер " + userEmail + " не найден среди соискателей");
    }

    @Override
    public void changeResume(String userEmail, InputResumeDto resume, InputContactInfoDto contacts) {
        if (isResumeInSystem(resume.getId())) {
            Integer userId = userService.getUserByEmail(userEmail).getId();

            Resume newResume = Resume.builder()
                    .id(resume.getId())
                    .userId(userId)
                    .name(resume.getName())
                    .updateTime(LocalDateTime.now())
                    .build();

            if (!resume.getCategory().equals("Выберите категорию")) {
                newResume.setCategoryId(Integer.parseInt(resume.getCategory()));
            }

            if (resume.getSalary() != null) {
                newResume.setSalary(resume.getSalary());
            }

            newResume.setIsActive(resume.getIsActive() != null);
            resumeDao.changeResume(newResume);
            contactsInfoService.updateContactInfo(newResume.getId(), contacts);
        } else {
            log.error("Резюме с айди " + resume.getId() + " не найдено в системе для его редактирования");
        }
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
        throw new ResumeNotFoundException("Резюме с айди " + id + " не найдено в системе для его удаления");
    }

    @Override
    public void getResume(String userEmail, int id, Model model) {
        ResumeDto resumeDto = getResumeById(id);

        if (userEmail.equals(resumeDto.getUserEmail())) {
            model.addAttribute("resume", resumeDto);
        } else {
            throw new ResumeNotFoundException("Несоответствие юзера и юзера в резюме");
        }
    }
}
