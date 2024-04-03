package com.example.jobsearch.service;

import com.example.jobsearch.dto.CategoryDto;

import java.util.List;


public interface CategoryService {
    CategoryDto getCategoryById(int id);
    String getParentCategory(Object id);
    List<String> getAllCategories();
    Integer checkInCategories(int categoryId);

    Integer checkInCategories(String category);

    Integer getCategoryByName(String category);
}
