package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.CategoryDao;
import com.example.jobsearch.dto.CategoryDto;
import com.example.jobsearch.exception.CategoryNotFoundException;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    @SneakyThrows
    @Override
    public CategoryDto getCategoryById(int id) {
        Category category = categoryDao.getCategoryById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Can not find category with id:" + id));

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

    @Override
    public List<String> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public Integer checkInCategories(int categoryId) {
        if (categoryDao.isCategoryInSystem(categoryId)) {
            return categoryId;
        }
        throw new CategoryNotFoundException("Не найдена категория с айди: " + categoryId);
    }

    @Override
    public Integer checkInCategories(String category) {
        return getCategoryByName(category);
    }

    @Override
    public Integer getCategoryByName(String category) {
        if (categoryDao.isCategoryInSystem(category)) {
            return categoryDao.getCategoryByName(category.toLowerCase().strip());
        }
        throw new CategoryNotFoundException("Не найдена категория: " + category);
    }
}
