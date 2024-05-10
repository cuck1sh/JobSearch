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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @SneakyThrows
    @Override
    public CategoryDto getCategoryById(Integer id) {
        if (id != null) {
            Optional<Category> category = categoryRepository.findById(id);

            return category.map(value -> CategoryDto.builder()
                    .id(value.getId())
                    .parent(getParentCategory(value.getParent()))
                    .name(value.getName())
                    .build()).orElse(null);
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
    public List<String> getStringedCategories() {
        List<Category> categories = getAllCategories();
        List<String> stringCategories = new ArrayList<>();

        stringCategories.add("Пусто");

        categories.forEach(e -> stringCategories.add(e.getName()));
        return stringCategories;
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
