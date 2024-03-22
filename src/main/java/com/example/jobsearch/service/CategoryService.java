package com.example.jobsearch.service;

import com.example.jobsearch.dto.CategoryDto;


public interface CategoryService {
    CategoryDto getCategoryById(int id);
    String getParentCategory(Object id);
}
