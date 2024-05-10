package com.example.jobsearch.service;

import com.example.jobsearch.dto.CategoryDto;
import com.example.jobsearch.model.Category;

import java.util.List;


public interface CategoryService {
    CategoryDto getCategoryById(Integer id);
    String getParentCategory(Object id);

    List<Category> getAllCategories();

    List<String> getStringedCategories();
    Integer checkInCategories(int categoryId);
    Integer checkInCategories(String category);
    Integer getCategoryByName(String category);
}
