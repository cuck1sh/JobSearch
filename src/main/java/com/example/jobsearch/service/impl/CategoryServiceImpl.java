package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.CategoryDao;
import com.example.jobsearch.dto.CategoryDto;
import com.example.jobsearch.exception.CategoryNotFoundException;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    @SneakyThrows
    @Override
    public CategoryDto getCategoryById(Integer id) {
        if (id != null) {
            Category category = categoryDao.getCategoryById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Can not find category with id:" + id));

            return CategoryDto.builder()
                    .id(category.getId())
                    .parent(getParentCategory(category.getParentId()))
                    .name(category.getName())
                    .build();
        } else {
            log.error("Не передан айди для категории");
            return null;
        }
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
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public Integer checkInCategories(int categoryId) {
        if (categoryDao.isCategoryInSystem(categoryId)) {
            return categoryId;
        } else {
            log.error("Не найдена категория с айди: " + categoryId);
            return null;
        }
    }

    @Override
    public Integer checkInCategories(String category) {
        return getCategoryByName(category);
    }

    @Override
    public Integer getCategoryByName(String category) {
        if (categoryDao.isCategoryInSystem(category)) {
            return categoryDao.getCategoryByName(category);
        } else {
            log.error("Не найдена категория: " + category);
            return null;
        }
    }
}
