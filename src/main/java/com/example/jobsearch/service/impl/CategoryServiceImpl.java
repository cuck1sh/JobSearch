package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.CategoryDao;
import com.example.jobsearch.dto.CategoryDto;
import com.example.jobsearch.exception.ResumeNotFoundException;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    @SneakyThrows
    @Override
    public CategoryDto getCategoryById(int id) {
        Category category = categoryDao.getCategoryById(id)
                .orElseThrow(() -> new ResumeNotFoundException("Can not find resume with id:" + id));

        return CategoryDto.builder()
                .id(category.getId())
                .parent(getParentCategory(category.getParentId()))
                .name(category.getName())
                .build();
    }

    @Override
    public String getParentCategory(Object entry) {
        if (entry != null) {
            int id = (int) entry;
            return categoryDao.getParentCategory(id);
        }
        return null;
    }
}
