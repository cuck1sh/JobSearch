package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.ResumeService;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public List<ResumeDto> getResumes() {
        List<Resume> resumes = resumeDao.getResumes();
        return getResumeDtos(resumes);
    }

    @Override
    public ResumeDto getResumeById(int id) throws UserNotFoundException {
        Resume resume = resumeDao.getResumeById(id).orElseThrow(() -> new UserNotFoundException("Can not find resume with id: " + id));
        return ResumeDto.builder()
                .id(resume.getId())
                .user(userService.getUserById(resume.getUserId()))
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
        return getResumeDtos(resumes);
    }

    @Override
    public List<ResumeDto> getResumesByUserEmail(String email) {
        List<Resume> resumes = resumeDao.getResumesByUserEmail(email);
        return getResumeDtos(resumes);
    }


    private List<ResumeDto> getResumeDtos(List<Resume> resumes) {
        List<ResumeDto> dtos = new ArrayList<>();
        resumes.forEach(e -> dtos.add(ResumeDto.builder()
                .id(e.getId())
                .user(userService.getUserById(e.getUserId()))
                .name(e.getName())
                .category(categoryService.getCategoryById(e.getCategoryId()))
                .salary(e.getSalary())
                .isActive(e.getIsActive())
                .createdDate(e.getCreatedDate())
                .updateTime(e.getUpdateTime())
                .build()));
        return dtos;
    }

    @SneakyThrows
    private boolean isEmployee(int userId) {
        return "Соискатель".equalsIgnoreCase(userService.getUserById(userId).getAccountType());
    }

    @Override
    public HttpStatus createResume(ResumeDto resume) {
        if (isEmployee(resume.getUser().getId())) {
            resumeDao.createResume(resume);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public Boolean isResumeInSystem(int id) {
        return resumeDao.isResumeInSystem(id);
    }

    @Override
    public HttpStatus changeResumeName(int id, String name) {
        if (isResumeInSystem(id)) {
            resumeDao.changeResumeName(id, name);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus changeResumeCategory(int id, String category) {
        if (isResumeInSystem(id)) {
            resumeDao.changeResumeCategory(id, category);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus changeResumeSalary(int id, Double salary) {
        if (isResumeInSystem(id)) {
            resumeDao.changeResumeSalary(id, salary);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus changeResumeActive(int id, Boolean status) {
        if (isResumeInSystem(id)) {
            resumeDao.changeResumeActive(id, status);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus deleteResumeById(int id) {
        if (isResumeInSystem(id)) {
            resumeDao.deleteResumeById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public List<ResumeDto> getActiveResumes(int userId) {
        // TODO реализовать выборку активных резюме
        return null;
    }

}
