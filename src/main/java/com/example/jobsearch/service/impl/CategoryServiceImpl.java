package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.CategoryDto;
import com.example.jobsearch.exception.CategoryNotFoundException;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.repository.CategoryRepository;
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

    private final CategoryRepository categoryRepository;

    @SneakyThrows
    @Override
    public CategoryDto getCategoryById(Integer id) {
        if (id != null) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Can not find category with id:" + id));

            return CategoryDto.builder()
                    .id(category.getId())
                    .parent(getParentCategory(category.getParent()))
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
            Category category = (Category) entry;
            return categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new CategoryNotFoundException("Can not find parent category with id:" + category.getId()))
                    .getName();
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Integer checkInCategories(int categoryId) {
        if (categoryRepository.existsById(categoryId)) {
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
        if (categoryRepository.existsCategoryByName(category)) {
            return categoryRepository.findCategoryByName(category)
                    .orElseThrow(() -> new CategoryNotFoundException("Can not find category:" + category))
                    .getId();
        } else {
            log.error("Не найдена категория: " + category);
            return null;
        }
    }
}
