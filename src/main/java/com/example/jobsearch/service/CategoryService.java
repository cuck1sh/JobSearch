package com.example.jobsearch.service;

import com.example.jobsearch.dto.CategoryDto;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    CategoryDto getCategoryById(int id);

    String getParentCategory(Object id);
}
